package ru.sinara.ganymede.engine;

public class TestResults {

    private Integer total = 0;

    private Integer failed = 0;

    private Integer passed = 0;


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    public Integer getPassed() {
        return passed;
    }

    public void addPassed(Integer inc) {
        passed += inc;
    }

    public Integer getFailed() {
        return failed;
    }

    public void addFailed(Integer inc) {
        failed += inc;
    }

    @Override
    public String toString() {
        return String.format("Total:\t%d%nPassed:\t%d%nFailed:\t%d", total, passed, failed);
    }

    public static TestResults create(Integer total) {
        TestResults testResults = new TestResults();
        if (total == null) return testResults;

        testResults.setTotal(total);
        return testResults;
    }

}
