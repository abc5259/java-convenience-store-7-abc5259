package store;

public class Application {
    public static void main(String[] args) {
        ConvenienceSystemConfig convenienceSystemConfig = new ConvenienceSystemConfig();
        ConvenienceSystemRunner convenienceSystemRunner = convenienceSystemConfig.convenienceSystemRunner();
        convenienceSystemRunner.run();
    }
}
