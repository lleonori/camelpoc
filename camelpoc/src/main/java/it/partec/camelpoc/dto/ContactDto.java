package it.partec.camelpoc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ";")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @DataField(pos = 1)
    private Integer id;
    @DataField(pos = 2)
    private Boolean flagContact;
    @DataField(pos = 3)
    private String name;
}