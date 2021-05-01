package com.upce.ar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Questions {
    private static Random rand = new Random();
    private ArrayList<String> true_names;
    private ArrayList<String> superhero_names;
    private ArrayList<String> superhero_equipment;
    private ArrayList<String> superhero_creators;
    private ArrayList<String> superhero_years;

    private ArrayList<Question> questionList;

    Questions() {
        true_names = new ArrayList<>();
        true_names.add("Tony Stark");
        true_names.add("Steve Rogers");
        true_names.add("Bruce Banner");
        true_names.add("T'Challa");
        true_names.add("Carol Danvers");
        true_names.add("Billy Batson");
        true_names.add("Peter Parker");
        true_names.add("Gwen Stacy");
        true_names.add("Clark Kent");
        true_names.add("Bruce Wayne");

        superhero_names = new ArrayList<>();
        superhero_names.add("Iron Man");
        superhero_names.add("Captain America");
        superhero_names.add("Hulk");
        superhero_names.add("Black Panther");
        superhero_names.add("Captain Marvel");
        superhero_names.add("Shazam");
        superhero_names.add("Spider man");
        superhero_names.add("Spider Gwen");
        superhero_names.add("Superman");
        superhero_names.add("Batman");
        superhero_names.add("Starlord");
        superhero_names.add("Thor");

        superhero_equipment = new ArrayList<>();
        superhero_equipment.add("Mjolnir");
        superhero_equipment.add("Storm breaker");
        superhero_equipment.add("Štít Kapitána Ameriky");
        superhero_equipment.add("Starlordova helma");
        superhero_equipment.add("Náhrdelník Black Panthera");
        superhero_equipment.add("Batarang");

        superhero_creators = new ArrayList<>();
        superhero_creators.add("Stan Lee");
        superhero_creators.add("Stan Lee a Jack Kirby");
        superhero_creators.add("Joe Simon a Jack Kirby");
        superhero_creators.add("C. C. Beck Bill Parker");
        superhero_creators.add("Roy Thomas a Gene Colan");
        superhero_creators.add("Stan Lee a Steve Ditko");
        superhero_creators.add("Steve Englehart a Steve Gan");
        superhero_creators.add("Jerry Siegel a Joe Shuster");

        superhero_years = new ArrayList<>();
        superhero_years.add("2008");
        superhero_years.add("2011");
        superhero_years.add("2003");
        superhero_years.add("2016");
        superhero_years.add("2019");
        superhero_years.add("2002");
        superhero_years.add("2018");
        superhero_years.add("1978");
        superhero_years.add("1966");
        superhero_years.add("2014");

        questionList = new ArrayList<>();
        //heroes
        questionList.add(new Question("Jak se jmenuje zobrazený hrdina?", "Iron Man", "iron_man", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje pravým jménem zobrazený \nhrdina?", "Tony Stark", "iron_man", EnumQuestionType.REAL_NAME));
        questionList.add(new Question("Kdo vytvořil zobrazeného superhrdinu?", "Stan Lee", "iron_man", EnumQuestionType.CREATOR));
        questionList.add(new Question("V kterém roce se hrdina poprvé objevil ve \nfilmu?", "2008", "iron_man", EnumQuestionType.FIRST_APPEARANCE_YEAR));

        questionList.add(new Question("Jak se jmenuje zobrazený hrdina?", "Spider man", "spiderman", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje pravým jménem zobrazený \nhrdina?", "Peter Parker", "spiderman", EnumQuestionType.REAL_NAME));
        questionList.add(new Question("Kdo vytvořil zobrazeného superhrdinu?", "Stan Lee a Steve Ditko", "spiderman", EnumQuestionType.CREATOR));
        questionList.add(new Question("V kterém roce se hrdina poprvé objevil ve \nfilmu?", "2002", "spíderman", EnumQuestionType.FIRST_APPEARANCE_YEAR));

        questionList.add(new Question("Jak se jmenuje zobrazený hrdina?", "Black panther", "black_panther", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje pravým jménem zobrazený \nhrdina?", "T'Challa", "black_panther", EnumQuestionType.REAL_NAME));
        questionList.add(new Question("Kdo vytvořil zobrazeného superhrdinu?", "Stan Lee a Jack Kirby", "black_panther", EnumQuestionType.CREATOR));
        questionList.add(new Question("V kterém roce se hrdina poprvé objevil ve \nfilmu?", "2016", "black_panther", EnumQuestionType.FIRST_APPEARANCE_YEAR));

        questionList.add(new Question("Jak se jmenuje zobrazený hrdina?", "Captain America", "captain_america", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje pravým jménem zobrazený \nhrdina?", "Steve Rogers", "captain_america", EnumQuestionType.REAL_NAME));
        questionList.add(new Question("Kdo vytvořil zobrazeného superhrdinu?", "Joe Simon a Jack Kirb", "captain_america", EnumQuestionType.CREATOR));
        questionList.add(new Question("V kterém roce se hrdina poprvé objevil ve \nfilmu?", "2011", "captain_america", EnumQuestionType.FIRST_APPEARANCE_YEAR));

        questionList.add(new Question("Jak se jmenuje zobrazený hrdina?", "Captain Marvel", "captain_marvel", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje pravým jménem zobrazený \nhrdina?", "Carol Danvers", "captain_marvel", EnumQuestionType.REAL_NAME));
        questionList.add(new Question("Kdo vytvořil zobrazeného superhrdinu?", "Roy Thomas a Gene Colan", "captain_marvel", EnumQuestionType.CREATOR));
        questionList.add(new Question("V kterém roce se hrdina poprvé objevil ve \nfilmu?", "2019", "captain_marvel", EnumQuestionType.FIRST_APPEARANCE_YEAR));

        questionList.add(new Question("Jak se jmenuje zobrazený hrdina?", "Hulk", "hulk", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje pravým jménem zobrazený \nhrdina?", "Bruce Banner", "hulk", EnumQuestionType.REAL_NAME));
        questionList.add(new Question("Kdo vytvořil zobrazeného superhrdinu?", "Stan Lee a Jack Kirby", "hulk", EnumQuestionType.CREATOR));
        questionList.add(new Question("V kterém roce se hrdina poprvé objevil ve \nfilmu?", "2003", "hulk", EnumQuestionType.FIRST_APPEARANCE_YEAR));

        questionList.add(new Question("Jak se jmenuje zobrazený hrdina?", "Shazam", "shazam", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje pravým jménem zobrazený \nhrdina?", "Billy Batson", "shazam", EnumQuestionType.REAL_NAME));
        questionList.add(new Question("Kdo vytvořil zobrazeného superhrdinu?", "C. C. Beck Bill Parker", "shazam", EnumQuestionType.CREATOR));
        questionList.add(new Question("V kterém roce se hrdina poprvé objevil ve \nfilmu?", "2019", "shazam", EnumQuestionType.FIRST_APPEARANCE_YEAR));

        questionList.add(new Question("Jak se jmenuje zobrazený hrdina?", "Spider Gwen", "spider_gwen", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje pravým jménem zobrazený \nhrdina?", "Gwen Stacy", "spider_gwen", EnumQuestionType.REAL_NAME));
        questionList.add(new Question("Kdo vytvořil zobrazeného superhrdinu?", "Stan Lee a Steve Ditko", "spider_gwen", EnumQuestionType.CREATOR));
        questionList.add(new Question("V kterém roce se hrdina poprvé objevil ve \nfilmu?", "2018", "spider_gwen", EnumQuestionType.FIRST_APPEARANCE_YEAR));

        questionList.add(new Question("Jak se jmenuje zobrazený hrdina?", "Batman", "batman", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje pravým jménem zobrazený \nhrdina?", "Bruce Wayne", "batman", EnumQuestionType.REAL_NAME));
        questionList.add(new Question("Kdo vytvořil zobrazeného superhrdinu?", "Bob Kane a Bill Finger", "batman", EnumQuestionType.CREATOR));
        questionList.add(new Question("V kterém roce se hrdina poprvé objevil ve \nfilmu?", "1966", "batman", EnumQuestionType.FIRST_APPEARANCE_YEAR));

        //equipment
        questionList.add(new Question("Kterému hrdinovi patří zobrazený objekt?", "Batman", "batarang", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje zobrazený objekt?", "Batarang", "batarang", EnumQuestionType.MOVIE_OBJECT));

        questionList.add(new Question("Kterému hrdinovi patří zobrazený objekt?", "Black Panther", "black_panther_necklace", EnumQuestionType.SUPERHERO_NAME));

        questionList.add(new Question("Kterému hrdinovi patří zobrazený objekt?", "Captain America", "captain_america_shield", EnumQuestionType.SUPERHERO_NAME));

        questionList.add(new Question("Kterému hrdinovi patří zobrazený objekt?", "Thor", "mjolnir", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje zobrazený objekt?", "Mjolnir", "mjolnir", EnumQuestionType.MOVIE_OBJECT));

        questionList.add(new Question("Kterému hrdinovi patří zobrazený objekt?", "Thor", "storm_breaker", EnumQuestionType.SUPERHERO_NAME));
        questionList.add(new Question("Jak se jmenuje zobrazený objekt?", "Storm breaker", "storm_breaker", EnumQuestionType.MOVIE_OBJECT));

        questionList.add(new Question("Kterému hrdinovi patří zobrazený objekt?", "Starlord", "star_lord_helmet", EnumQuestionType.SUPERHERO_NAME));

    }

    Question getRandomQuestion() {
        List<Question> q = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            if (!questionList.get(i).isAlreadyAnswered()) {
                q.add(questionList.get(i));
            }
        }
        if (q.isEmpty()) {
            return null;
        }
        return q.get(rand.nextInt(q.size() - 1));
    }

    int getCountQuestions() {
        int count = 0;
        for (int i = 0; i < questionList.size(); i++) {
            if (questionList.get(i).isAlreadyAnswered()) {
                count += 1;
            }
        }
        return count;
    }

    public class Question {
        private String question;
        private String answer;
        private String model;
        private boolean alreadyAnswered;
        private int attempts;
        EnumQuestionType typeOfQuestion;

        Question(String question, String answer, String model, EnumQuestionType type) {
            this.question = question;
            this.answer = answer;
            this.model = model;
            alreadyAnswered = false;
            this.typeOfQuestion = type;
        }

        int getAttempts() {
            return attempts;
        }

        void addAttempts() {
            attempts++;
        }

        String getAnswer() {
            return answer;
        }

        void setAlreadyAnswered(boolean alreadyAnswered) {
            this.alreadyAnswered = alreadyAnswered;
        }

        boolean isAlreadyAnswered() {
            return alreadyAnswered;
        }

        public String getModel() {
            return model;
        }

        String getQuestion() {
            return question;
        }

        ArrayList<String> getPossibleAnswers() {
            ArrayList<String> answers = null;
            switch (this.typeOfQuestion) {
                case CREATOR:
                    answers = new ArrayList<>(superhero_creators);
                    break;
                case FIRST_APPEARANCE_YEAR:
                    answers = new ArrayList<>(superhero_years);
                    break;
                case MOVIE_OBJECT:
                    answers = new ArrayList<>(superhero_equipment);
                    break;
                case REAL_NAME:
                    answers = new ArrayList<>(true_names);
                    break;
                case SUPERHERO_NAME:
                    answers = new ArrayList<>(superhero_names);
                    break;
            }
            answers.remove(this.answer);

            Collections.shuffle(answers);
            while (answers.size() > 3) {
                answers.remove(3);
            }
            answers.add(this.answer);
            Collections.shuffle(answers);
            return answers;
        }
    }
}
