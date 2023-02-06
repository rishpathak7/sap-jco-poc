package jco.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OUTPUT{
    @JsonProperty("RETURN") 
    public RETURN rETURN;
    @JsonProperty("EXCH_RATE") 
    public EXCHRATE eXCH_RATE;
}
