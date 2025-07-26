package ru.sinara.ganymede.engine;

public class Summary {

    public static Summary getInstance(Integer total) {
        Summary summary = new Summary();
        if (total == null) return summary;

        summary.setTotal(total);
        return summary;
    }

    private Integer total = 0;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    private Integer passed = 0;

    public Integer getPassed() {
        return passed;
    }

    public void addPassed(Integer inc) {
        passed +=inc;
    }

    private Integer failed = 0;

    public Integer getFailed() {
        return failed;
    }


    public void addFailed(Integer inc) {
        failed +=inc;
    }

}
