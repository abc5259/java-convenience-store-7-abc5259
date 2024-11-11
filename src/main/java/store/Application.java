package store;

import store.converter.StringToPromotionConverter;
import store.converter.StringToTempProductConverter;
import store.initializer.ConvenienceSystemInitializer;
import store.initializer.ProductsInitializer;
import store.initializer.PromotionsInitializer;
import store.io.FileReader;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        IteratorInputTemplate iteratorInputTemplate = new IteratorInputTemplate(outputView);
        IteratorInputHandler iteratorInputHandler = new IteratorInputHandler(inputView, iteratorInputTemplate);
        FileReader fileReader = new FileReader();
        PromotionsInitializer promotionsInitializer = new PromotionsInitializer(fileReader,
                new StringToPromotionConverter());
        ProductsInitializer productsInitializer = new ProductsInitializer(fileReader,
                new StringToTempProductConverter());
        ConvenienceSystemInitializer convenienceSystemInitializer = ConvenienceSystemInitializer.initialize(
                promotionsInitializer,
                productsInitializer);
        ConvenienceSystemRunner convenienceSystemRunner = new ConvenienceSystemRunner(convenienceSystemInitializer,
                iteratorInputHandler, outputView);
        convenienceSystemRunner.run();
    }
}
