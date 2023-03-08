package cc.mrbird.febs.fs.entity;

import lombok.Data;

@Data
public class FsBReport {
    private String id;
    private String userAccount;
    private String userAccountName;
    private String number;
    private int count;
    private String year;
    private String quarter;
    private int total;
    private String  hdjlpgs;
    private String note;
    private int ling;
    private int back;
}
