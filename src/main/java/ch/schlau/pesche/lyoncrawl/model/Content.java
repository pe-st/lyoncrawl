package ch.schlau.pesche.lyoncrawl.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Content {

    String id;
    String type;
    String title;

    /**
     * expandable property: `?expand=history`
     */
    ContentHistory history;

}
