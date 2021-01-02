package ch.schlau.pesche.lyoncrawl.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentArray {

    List<Content> results;

    Integer start;
    Integer limit;
    Integer size;
}
