        import com.fasterxml.jackson.databind.util.ArrayBuilders;
        import org.jsoup.nodes.Document;
        import org.telegram.telegrambots.bots.TelegramLongPollingBot;
        import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
        import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
        import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
        import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
        import org.telegram.telegrambots.meta.api.objects.File;
        import org.telegram.telegrambots.meta.api.objects.Message;
        import org.telegram.telegrambots.meta.api.objects.Update;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
        import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Element;
        import org.jsoup.select.Elements;

        import java.io.*;
        import java.lang.reflect.Type;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLConnection;
        import java.util.ArrayList;
        import java.util.List;
        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.Date;

        import static java.lang.Math.toIntExact;

        /**
 * Created by Sviat on 25.10.2018.
 */
public class W_585_bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
//        Sending sending = new Sending();

        Service service = new Service();
        service.sendAllService(update);

//        ServicesManager servicesManager = new  ServicesManager();
//        servicesManager.addService(update.getMessage().getText());

//        if (update.hasMessage() && update.getMessage().hasText()) {
//            sending.sendMainMenu(update);
//        } else if (update.hasCallbackQuery()) {
//            MyCallBackQuerty.getInstance().switchByMenus(update);
//        }



//        if (update.getMessage().getText().equals("country")){
//            CountryManager.getInstance().printAllCountries();
//        }else {
//            Product.viewProduct(update, UsersManager.getInstance().getProductByPrice(Float.parseFloat(update.getMessage().getText())));
//        }
//        if (update.hasMessage()){
//            String result = UsersManager.getInstance().getNameByChat(update.getMessage().getChat());
//            System.out.printf(result);
//            messages.sendMsg(update, "Hi, I can help you");
//        }
    }
    public String getBotUsername() {
        return "W_585_bot";
    }
    public String getBotToken() {
        return "766701035:AAE0xQEZR_eCG_oUdjig678AWHKmcbOPrLE";
    }
}
