package com.warkahot.nitjcompanion;

/**
 * Created by warkahot on 18-Sep-16.
 */
public class Placements_list  {

    String company_name,post,lpa,branch,students;

    public Placements_list(String company_name, String post, String lpa, String branch, String students) {
        this.company_name = company_name;
        this.post = post;
        this.lpa = lpa;
        this.branch = branch;
        this.students = students;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getPost() {
        return post;
    }

    public String getLpa() {
        return lpa;
    }

    public String getBranch() {
        return branch;
    }

    public String getStudents() {
        return students;
    }

    public Placements_list() {

    }
}
