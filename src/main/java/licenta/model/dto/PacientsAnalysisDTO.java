package licenta.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import licenta.model.Analysis;

import java.io.Serializable;
import java.util.List;

public class PacientsAnalysisDTO implements Serializable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("analysis")
    private List<Analysis> analysis;

    public PacientsAnalysisDTO() {
        // Default constructor
    }
    public PacientsAnalysisDTO(Long id, String name, String description, List<Analysis> analysis) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.analysis = analysis;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAnalysis(List<Analysis> analysis) {
        this.analysis = analysis;
    }
}
