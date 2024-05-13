import java.util.Random;

public class Main {
    public static int bossHealth = 1500;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250, 350, 560, 200, 350, 300};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 7, 0, 10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky",
            "Witcher", "Thor"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        showStatistics();
        System.out.print("\n");
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void healing() {
        int medicSkill = 30;

        Random random = new Random();
        int skipMedic = 3;
        int luckyIndex;
        do {
            luckyIndex = random.nextInt(heroesHealth.length);
        } while (luckyIndex == skipMedic);

        if ((heroesHealth[luckyIndex] < 100) && heroesHealth[luckyIndex] > 0) {
            heroesHealth[luckyIndex] = heroesHealth[luckyIndex] + medicSkill;
            System.out.println(heroesAttackType[luckyIndex] + " healing " + heroesHealth[luckyIndex]);
        }


    }



    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();


        System.out.print("\n");
        showStatistics();
        System.out.print("\n");

        if (heroesHealth[3] > 0) {
            healing();
            Resurrect();
        }

    }

    public static void Resurrect() {
        int witcherHp = heroesHealth[6];

        for (int i = 0; i < heroesHealth.length; i++) {

            if (heroesHealth[i] <= 0 && heroesHealth[6] > 0) {
                heroesHealth[i] = heroesHealth[i] + witcherHp;
                heroesHealth[6] = 0;


                System.out.println("Witcher resurrect: " + heroesAttackType[i] + " hero");
                break;
            }
        }

    }


    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(8) + 2; // 2, 3, 4, 5, 6, 7, 8, 9
                    damage = heroesDamage[i] * coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }


    public static void bossAttacks() {

        int golemLive = heroesHealth[4];
        int golemDefend = bossDamage / 5;
        int bossDamageMinimal = bossDamage - golemDefend;
        int allHeroSun = heroesAttackType.length;
        int damageForGolem = allHeroSun * golemDefend;

        Random random = new Random();
        double luckyMiss = 0.4; // 40%
        int luckyIndex = 5;
        boolean bossStun = random.nextBoolean();

        for (int i = 0; i < heroesHealth.length; i++) {
            if (bossStun) {
                System.out.println("Thor Stunned BOSS");
                break;
            }

            if (heroesHealth[i] > 0) {
                if (i == luckyIndex && random.nextDouble() < luckyMiss) {
                    System.out.println("BOSS missed attack on Lucky Hero");
                    continue;
                }
            }

            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;

                } else {
                    if (heroesHealth[4] - bossDamage < 0) {
                        heroesHealth[4] = 0;
                    }
                    if (golemLive > 0) {
                        heroesHealth[i] = heroesHealth[i] - bossDamageMinimal;

                        if (golemLive > 0) {
                            heroesHealth[4] = golemLive - damageForGolem;
                        }
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: "
                + bossDamage + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " damage: " + heroesDamage[i]);
//
        }
    }
}