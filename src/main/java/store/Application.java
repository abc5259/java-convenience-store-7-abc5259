package store;

import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        IteratorInputTemplate iteratorInputTemplate = new IteratorInputTemplate(outputView);
        IteratorInputHandler iteratorInputHandler = new IteratorInputHandler(inputView, iteratorInputTemplate);
        ConvenienceSystemRunner convenienceSystemRunner = new ConvenienceSystemRunner(iteratorInputHandler, outputView);
        convenienceSystemRunner.run();
    }
}
