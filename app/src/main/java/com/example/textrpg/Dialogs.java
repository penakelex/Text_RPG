package com.example.textrpg;


public class Dialogs {
    private final String[] names = {"", "Регистратор"};
    Quote[] quotes0 = new Quote[]{
            new Quote(names[0], "*Вы, наконец, получили возможность стать космонавтом.\nВы заходите в кабинет для регистрации Ваших данных для участия в конкурсе.*",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Пройти к столу регистрации*", 0, 1),
                            new Quote.CharacterQuote("*Струсить и уйти*", 0, 4)}),
            new Quote(names[1], "Здравствуйте, присаживайтесь.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Здравствуйте. *Присесть*", 0, 2),
                            new Quote.CharacterQuote("*Струсить и уйти*", 0, 4)}),
            new Quote(names[1], "Давайте мне свои документы",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("*Дать нужные документы*", 0, 3),
                            new Quote.CharacterQuote("С чего бы мне Вам давать свои документы?", 0, 6)}),
            new Quote(names[1], "Заполните анкету о себе, пока я сканирую Ваши документы",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Хорошо. Давайте.", 0, -1),
                            new Quote.CharacterQuote("Ну уж нет. Вам самим трудно что ли?", 0, 7),
                            new Quote.CharacterQuote("По-моему Вы берёте с меня слишком много информации. Вам мало того, что я уже дал.", 0, 5)}),
            new Quote(names[1], "Постойте! Куда Вы пошли?", new Quote.CharacterQuote[]{
                    new Quote.CharacterQuote("*Собраться и подойти к столу регистрации*", 0, 1)}),
            new Quote(names[1], "Если Вы хотите участвовать в конкурсе, то мы должны заполнить Ваши данные.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Ладно, давайте...", 0, -1)}),
            new Quote(names[1], "Если Вы хотите участвовать в конкурсе, то мы должны заполнить Ваши данные.",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Ладно, держите...", 0, 3)}),
            new Quote(names[1], "Согласитесь, если я буду их заполнять, то не смогу написать правильно. Поэтому заполните их, пожалуйста, сами",
                    new Quote.CharacterQuote[]{new Quote.CharacterQuote("Ладно, давайте...", 0, -1)})
    };


    //Отрицательные шаги: -1: Для перехода на заполнение данных о персонаже
    private final Quote[][] quotes = {quotes0};

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

        public Quote(String name, String quote, CharacterQuote[] characterQuotes) {
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



