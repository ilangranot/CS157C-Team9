public class GraphStats {
    private String operation;
    private long linesRead;
    private long linesProcessed;
    private long distinctUsers;
    private GraphStatus status;

    public GraphStats() {

    }

    public void readLine() {
        linesRead++;
    }

    public void processedLine() {
        linesProcessed++;
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
