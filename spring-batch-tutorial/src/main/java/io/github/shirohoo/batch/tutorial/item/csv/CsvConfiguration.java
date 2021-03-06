package io.github.shirohoo.batch.tutorial.item.csv;

import io.github.shirohoo.batch.tutorial.model.dto.Member;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import static java.lang.Integer.parseInt;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsvConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final String JOB_NAME = "csvJob";
    private static final String STEP_ONE = "csvStep";

    private static final String ENCODING = "UTF-8";
    private static final String CSV_ITEM_READER = "csvItemReader";
    private static final String CSV_ITEM_WRITER = "csvItemWriter";
    private static final String INPUT_PATH = "csv/input/member.csv";
    private static final String OUTPUT_PATH = "src/main/resources/csv/output/member.csv";

    private static final String HEADER = "id,name,age,address";
    //    private static final String FOOTER = "-------------------\n"; // append(true)??? ?????? footer??? ??????????????? ???????????? ???????????? ?????????
    private static final String FOOTER = "-------------------";

    @Bean
    public Job csvJob() throws Exception {
        return jobBuilderFactory.get(JOB_NAME)
                                .incrementer(new RunIdIncrementer())
                                .start(csvStep(null))
                                .build();
    }

    @Bean
    @JobScope
    public Step csvStep(@Value("#{jobParameters[chunkSize]}") String value) throws Exception {
        return stepBuilderFactory.get(STEP_ONE)
                                 .<Member, Member>chunk(getChunkSize(value))
                                 .reader(this.csvItemReader())
                                 .writer(this.csvItemWriter())
                                 .build();
    }

    /**
     * Batch-Application ?????? ??? ?????? ???????????? chunk size ??????
     * ?????? ??? ??????????????? ????????? size=10?????? ??????
     * -chunkSize=20 --job.name=taskletProcessing
     */
    private int getChunkSize(String value) {
        return StringUtils.isNotEmpty(value) ? parseInt(value) : 10;
    }

    private FlatFileItemReader<Member> csvItemReader() throws Exception {
        DefaultLineMapper<Member> mapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        tokenizer.setNames("id", "name", "age", "address");
        mapper.setLineTokenizer(tokenizer);

        mapper.setFieldSetMapper(fieldSet->{
            int id = fieldSet.readInt("id");
            String name = fieldSet.readString("name");
            int age = fieldSet.readInt("age");
            String address = fieldSet.readString("address");
            return Member.of(id, name, age, address);
        });

        FlatFileItemReader<Member> itemReader = new FlatFileItemReaderBuilder<Member>()
                .name(CSV_ITEM_READER)
                .encoding(ENCODING)
                .resource(new ClassPathResource(INPUT_PATH)) // ClassPathResource (Reader ????????? ???)
                .linesToSkip(1) // csv????????? ??? row??? ?????? ???????????????????????? ??????
                .lineMapper(mapper)
                .build();

        itemReader.afterPropertiesSet(); // itemReader ???????????????
        return itemReader;
    }

    private ItemWriter<Member> csvItemWriter() throws Exception {
        BeanWrapperFieldExtractor<Member> fieldExtractor = new BeanWrapperFieldExtractor<>();
        DelimitedLineAggregator<Member> aggregator = new DelimitedLineAggregator<>();

        fieldExtractor.setNames(new String[] {"id", "name", "age", "address"});
        aggregator.setDelimiter(",");
        aggregator.setFieldExtractor(fieldExtractor);

        FlatFileItemWriter<Member> itemWriter = new FlatFileItemWriterBuilder<Member>()
                .name(CSV_ITEM_WRITER)
                .encoding(ENCODING)
                .resource(new FileSystemResource(OUTPUT_PATH)) // FileSystemResource (Writer ????????? ???)
                .lineAggregator(aggregator)
                .headerCallback(writer->writer.write(HEADER)) // csv ???????????? ?????? ??????
                .footerCallback(writer->writer.write(FOOTER)) // csv ???????????? ?????? ??????
                .append(true) // ??? ????????? ?????? ????????? ????????? ??????????????? ??????, ?????? ????????? ????????? ????????? ?????????????????? ?????????
                .build();

        itemWriter.afterPropertiesSet();
        return itemWriter;
    }
}
