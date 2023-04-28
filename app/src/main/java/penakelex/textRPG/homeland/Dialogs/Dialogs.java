package penakelex.textRPG.homeland.Dialogs;




import static penakelex.textRPG.homeland.Constants.Homeland_Tag;

import android.app.PendingIntent;
import android.content.SharedPreferences;

public class Dialogs {
    private final String[] names = {"", "Регистратор", "Инструктор Сердцев"};
    Quote[] quotes0 = new Quote[]{
            new Quote(0, names[0], "*Вы, наконец, получили возможность стать космонавтом.\nВы заходите в кабинет для регистрации Ваших данных для участия в конкурсе.*",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Пройти к столу регистрации*", 0, 1),
                            new Quote.CharacterQuote("*Уйти*", 0, 4)}),
            new Quote(1, names[1], "Здравствуйте, присаживайтесь.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Здравствуйте. *Присесть*", 0, 2),
                            new Quote.CharacterQuote("*Уйти*", 0, 4)}),
            new Quote(1, names[1], "Дайте мне свои документы, которые были указаны для регистрации.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Дать нужные документы*", 0, 3),
                            new Quote.CharacterQuote("С чего бы мне Вам давать свои документы?", 0, 6)}),
            new Quote(1, names[1], "Заполните анкету о себе, пока я сканирую Ваши документы.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Хорошо. Давайте.", 0, -1),
                            new Quote.CharacterQuote("Ну уж нет. Вам самим трудно что ли?", 0, 7),
                            new Quote.CharacterQuote("По-моему Вы берёте с меня слишком много информации. Вам мало того, что я уже дал.", 0, 5)}),
            new Quote(1, names[1], "Постойте! Куда Вы пошли?", new Quote.CharacterQuote[]{
                    new Quote.CharacterQuote("*Подойти к столу регистрации*", 0, 1)}),
            new Quote(1, names[1], "Если Вы хотите участвовать в конкурсе, то мы должны заполнить Ваши данные.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Ладно, давайте...", 0, -1)}),
            new Quote(1, names[1], "Если Вы хотите участвовать в конкурсе, то мы должны заполнить Ваши данные.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Ладно, держите...", 0, 3)}),
            new Quote(1, names[1], "Согласитесь, если я буду их заполнять, то не смогу написать правильно. Поэтому заполните их, пожалуйста, сами",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Ладно, давайте...", 0, -1)})
    };
    Quote[] quotes1 = new Quote[]{
            new Quote(1, names[1], "",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Анкета завершена!", 0, 1)}),
            new Quote(1, names[1], "Отлично! Дайте взглянуть.",
                    new Quote.CharacterQuote[]{
                            new Quote.CharacterQuote("Держите.", 0, 2),
                            new Quote.CharacterQuote("Это мои личные данные, я не хочу, чтобы Вы их смотрели!", 0, 4),
                    }),
            new Quote(0, names[0], "*2 минуты спустя*",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Продолжить*", 0, 3)}),
            new Quote(1, names[1], "На основе заполненной Вами анкеты, нам нужно прикрепить и прочие сведения о Вас. Вам нужно расписаться в этой форме.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Давайте посмотрим.", 0, -3)}),
            new Quote(1, names[1], "Эти *данные* нужно будет также прикрепить к остальным, мне необходимо их заполнить, чтобы Вы потом проверили их.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("В таком случае держите...", 0, 2)})
    };
    Quote[] quotes2 = new Quote[]{
            new Quote(1, names[1], "Что-то непонятно?",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Что мне нужно вписать в подпись?", 0, 1),
                            new Quote.CharacterQuote("Зачем нужны урон оружием ближнего боя, критический урон?", 0, 2),
                            new Quote.CharacterQuote("Как Вы определили то, что написано в этой *форме*?", 0, 3)}),
            new Quote(1, names[1], "Обычно там ставят подпись, но можете просто написать своё имя.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Ладно, тогда напишу имя...", 0, -4),
                            new Quote.CharacterQuote("Не напомните мне моё имя?", 0, 7),
                            new Quote.CharacterQuote("У меня ещё есть несколько вопросов...", 0, 4)}),
            new Quote(1, names[1], "Я... честно сказать не знаю, но раз так нужно, то нужно заполнить, извините за тавтологию",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Ладно...", 0, 5)}),
            new Quote(1, names[1], "У нас для этого есть анализатор, который имеет огромную базу данных.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Что-то на заумном, но да ладно.", 0, 6)}),
            new Quote(1, names[1], "Да, конечно, что Вы хотели бы ещё узнать?",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Зачем нужны урон оружием ближнего боя, критический урон?", 0, 2),
                            new Quote.CharacterQuote("Как Вы определили то, что написано в этой *форме*?", 0, 3)}),
            new Quote(1, names[1], "В чём дело?",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Что мне нужно вписать в подпись?", 0, 1),
                            new Quote.CharacterQuote("Как Вы определили то, что написано в этой *форме*?", 0, 3)}),
            new Quote(1, names[1], "Что-нибудь ещё?",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Что мне нужно вписать в подпись?", 0, 1),
                            new Quote.CharacterQuote("Зачем нужны урон оружием ближнего боя, критический урон?", 0, 2)}),
            new Quote(1, names[1], "...\nВы забыли как Вас зовут?",
                    new Quote.CharacterQuote[] { new Quote.CharacterQuote("Нет, что Вы я проверяю Вас...", 0, 8)}),
            new Quote(1, names[1], "Ваше имя - 1",
                    new Quote.CharacterQuote[] { new Quote.CharacterQuote("Хорошо...", 0, 1)})
    };

    Quote[] quotes3 = new Quote[]{
            new Quote(1, names[1], "Вот всё и заполненно, можете забирать свои документы.",
                    new Quote.CharacterQuote[]{
                            new Quote.CharacterQuote("Ура! Спасибо! Всего хорошего!", 0, 1),
                            new Quote.CharacterQuote("Наконец это закончилось. Благодарен за помощь. До свидания.", 0, 1)}),
            new Quote(1, names[1], "Всего хорошего!",
                    new Quote.CharacterQuote[]{
                            new Quote.CharacterQuote("*Уйти*", 0, 2),
                            new Quote.CharacterQuote("*Посидеть ещё*", 0, 3)}),
            new Quote(0, names[0], "*Выходя, Вы видите большую очередь, которая идёт за Вами*",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Продолжить*", 0, 4)}),
            new Quote(1, names[1], "Что такое? Вы что-то хотите ещё спросить или что?",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Да нет, просто стул удобный... *Уйти*", 0, 2),
                            new Quote.CharacterQuote("Да нет, сейчас уже пойду... *Уйти*", 0, 2)}),
            new Quote(0, names[0], "*Спустя неделю*",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Продолжить*", 0, 5)}),
            new Quote(0, names[0], "*Вы смотрите результаты конкурса и понимаете, что Вас приняли в космонавтику*",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Как классно!*", 0, 6),
                            new Quote.CharacterQuote("Неплохо...*", 0, 6)}),
            new Quote(0, names[0], "*Спустя пару дней*",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Продолжить", 0, 7)}),
            new Quote(0, names[2], "Приветствую всех теперь вы приблизились к звёздам, но несмотря на это вам предстоит ещё много подготовки.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Продолжить", 0, 8)}),
            new Quote(0, names[2], "С сегодняшнего дня мы начинаем тренировки, от ваших результатов здесь будут зависеть исход ваших экспедиций в космос.",
                    new Quote.CharacterQuote[]{})
    };


    private final Quote[][] quotes = {quotes0, quotes1, quotes2};

    public Quote[] getQuotes(int index) {
        if (index >= 0 && index < quotes.length) {
            return quotes[index];
        }
        return null;
    }

    public Quote getQuote(int index, int ID) {
        return quotes[ID][index];
    }


    public static class Quote {
        private final String quote, name;
        private final CharacterQuote[] characterQuotes;
        private final int image;

        public int getImage() {
            return image;
        }

        public Quote(int image, String name, String quote, CharacterQuote[] characterQuotes) {
            this.image = image;
            this.quote = quote;
            this.characterQuotes = characterQuotes;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getQuote() {
            return quote;
        }

        public CharacterQuote[] getCharacterQuotes() {
            return characterQuotes;
        }

        public static class CharacterQuote {
            private final String checking, quote;
            private final int reputation, nextStep, checkingValue, money;

            public int getMoney() {
                return money;
            }

            public int getCheckingValue() {
                return checkingValue;
            }

            public String getChecking() {
                return checking;
            }

            public String getQuote() {
                return quote;
            }

            public int getReputation() {
                return reputation;
            }

            public int getNextStep() {
                return nextStep;
            }

            public CharacterQuote(String quote, int reputation, int nextStep, String checking, int checkingValue) {
                this.quote = quote;
                this.reputation = reputation;
                this.nextStep = nextStep;
                this.checking = checking;
                this.checkingValue = checkingValue;
                this.money = 0;
            }

            public CharacterQuote(String quote, int reputation, int nextStep) {
                this.quote = quote;
                this.reputation = reputation;
                this.nextStep = nextStep;
                this.checking = "";
                this.checkingValue = 0;
                this.money = 0;
            }

            public CharacterQuote(String quote, int reputation, int nextStep, String checking, int checkingValue, int money) {
                this.quote = quote;
                this.reputation = reputation;
                this.nextStep = nextStep;
                this.checking = checking;
                this.checkingValue = checkingValue;
                this.money = money;
            }

            public CharacterQuote(String quote, int reputation, int nextStep, int money) {
                this.quote = quote;
                this.reputation = reputation;
                this.nextStep = nextStep;
                this.checking = "";
                this.checkingValue = 0;
                this.money = money;
            }
        }
    }
}



