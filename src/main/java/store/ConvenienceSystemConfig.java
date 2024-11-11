package store;

import store.converter.StringToPromotionConverter;
import store.converter.StringToTempProductConverter;
import store.initializer.ConvenienceSystemInitializer;
import store.initializer.ProductsInitializer;
import store.initializer.PromotionsInitializer;
import store.initializer.StoreInitializer;
import store.io.FileReader;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceSystemConfig {

    private InputView inputView;
    private OutputView outputView;
    private IteratorInputTemplate iteratorInputTemplate;
    private IteratorInputHandler iteratorInputHandler;
    private FileReader fileReader;
    private PromotionsInitializer promotionsInitializer;
    private ProductsInitializer productsInitializer;
    private StoreInitializer storeInitializer;
    private ConvenienceSystemInitializer convenienceSystemInitializer;
    private ConvenienceSystemRunner convenienceSystemRunner;


    public InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    public OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }

    public IteratorInputTemplate iteratorInputTemplate() {
        if (iteratorInputTemplate == null) {
            iteratorInputTemplate = new IteratorInputTemplate(outputView());
        }
        return iteratorInputTemplate;
    }

    public IteratorInputHandler iteratorInputHandler() {
        if (iteratorInputHandler == null) {
            iteratorInputHandler = new IteratorInputHandler(inputView(), iteratorInputTemplate());
        }
        return iteratorInputHandler;
    }

    public FileReader fileReader() {
        if (fileReader == null) {
            fileReader = new FileReader();
        }
        return fileReader;
    }

    public PromotionsInitializer promotionsInitializer() {
        if (promotionsInitializer == null) {
            promotionsInitializer = new PromotionsInitializer(fileReader(), new StringToPromotionConverter());
        }
        return promotionsInitializer;
    }

    public ProductsInitializer productsInitializer() {
        if (productsInitializer == null) {
            productsInitializer = new ProductsInitializer(fileReader(), new StringToTempProductConverter());
        }
        return productsInitializer;
    }

    public StoreInitializer storeInitializer() {
        if (storeInitializer == null) {
            storeInitializer = new StoreInitializer(
                    promotionsInitializer().initialize(),
                    productsInitializer().initialize());
        }
        return storeInitializer;
    }

    public ConvenienceSystemInitializer convenienceSystemInitializer() {
        if (convenienceSystemInitializer == null) {
            convenienceSystemInitializer = new ConvenienceSystemInitializer(storeInitializer());
        }
        return convenienceSystemInitializer;
    }

    public ConvenienceSystemRunner convenienceSystemRunner() {
        if (convenienceSystemRunner == null) {
            convenienceSystemRunner = new ConvenienceSystemRunner(
                    convenienceSystemInitializer(),
                    iteratorInputHandler(),
                    outputView()
            );
        }
        return convenienceSystemRunner;
    }
}
