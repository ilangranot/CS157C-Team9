package GraphWum;

public class GraphStats {
    private String operation;
    private long linesRead;
    private long linesProcessed;
    private long distinctUsers;
    private GraphStatus status = GraphStatus.STANDBY;
    private String page;

    public GraphStats(String page) {
        this.page = page;
    }

    public void readLine() {
        linesRead++;
    }

    public void processedLine() {
        linesProcessed++;
    }

    public void setDistinctUsers() {
        distinctUsers++;
    }

    public long getDistinctUsers() {
        return distinctUsers;
    }

    public long getLinesProcessed() {
        return linesProcessed;
    }

    public long getLinesRead() {
        return linesRead;
    }

    public GraphStatus getStatus() {
        return status;
    }

    public void setStatus(GraphStatus val) {
        status = val;
    }
}