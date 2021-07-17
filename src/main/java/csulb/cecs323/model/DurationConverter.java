package csulb.cecs323.model;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;



/**
 *  This class implements a converter between a Duration object and Long
 *
 *  CITATION: https://thorben-janssen.com/jpa-tips-map-duration-attribute/
 *  Followed guide on setting up the AttributeConverter
 */
@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Long> {


    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        return attribute.toMinutes();
    }

    @Override
    public Duration convertToEntityAttribute(Long duration) {
        return Duration.ofMinutes(duration);
    }
}
