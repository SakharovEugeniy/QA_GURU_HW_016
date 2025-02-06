package models.changesWithUser;

import lombok.Data;

@Data
public class ResponseCreateBody {
    private String name;
    private String job;
    private String id;
    private String createdAt;
}
