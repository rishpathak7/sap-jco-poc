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
public class EXCHRATE{
    @JsonProperty("TO_CURRNCY") 
    public String tO_CURRNCY;
    @JsonProperty("FROM_FACTOR_V") 
    public int fROM_FACTOR_V;
    @JsonProperty("TO_FACTOR_V") 
    public int tO_FACTOR_V;
    @JsonProperty("FROM_CURR") 
    public String fROM_CURR;
    @JsonProperty("EXCH_RATE_V") 
    public int eXCH_RATE_V;
    @JsonProperty("FROM_FACTOR") 
    public int fROM_FACTOR;
    @JsonProperty("TO_FACTOR") 
    public int tO_FACTOR;
    @JsonProperty("VALID_FROM") 
    public String vALID_FROM;
    @JsonProperty("RATE_TYPE") 
    public String rATE_TYPE;
    @JsonProperty("EXCH_RATE") 
    public int eXCH_RATE;
}
