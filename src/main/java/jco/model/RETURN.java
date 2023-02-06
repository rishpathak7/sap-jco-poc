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
public class RETURN{
    @JsonProperty("MESSAGE_V2") 
    public String mESSAGE_V2;
    @JsonProperty("MESSAGE") 
    public String mESSAGE;
    @JsonProperty("MESSAGE_V3") 
    public String mESSAGE_V3;
    @JsonProperty("NUMBER") 
    public String nUMBER;
    @JsonProperty("MESSAGE_V4") 
    public String mESSAGE_V4;
    @JsonProperty("LOG_NO") 
    public String lOG_NO;
    @JsonProperty("MESSAGE_V1") 
    public String mESSAGE_V1;
    @JsonProperty("ID") 
    public String iD;
    @JsonProperty("TYPE") 
    public String tYPE;
    @JsonProperty("LOG_MSG_NO") 
    public String lOG_MSG_NO;
}
