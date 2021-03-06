package algos;

import village.Random;
import village.Village;
import village.Villager;

public class AlgoMalthus extends Algo {

	private int health;
	private boolean dude;
	private int babyCoolDown;
	private static final int MAX_BABIES = 5;
	private int kids;
	private static final int LIFE_EXPECTANCY = 60;
	private static final int MAX_HEALTH_FOR_BABIES = 65;
	private static final int MAX_TIME_TO_MAKE_BABY = 15;
	private int healthToDie;

	private Climat climat;
	private static final float PAS = 0.0000001f;
	private boolean firstTime = true;

	public AlgoMalthus(boolean dude, Villager villager){

		if(firstTime){
			firstTime = false;

			climat = villager.getVillage().climat;
		}

		health = (int) (Math.random() * (climat.difficulty * LIFE_EXPECTANCY));
		this.dude = dude;
		kids = 0;

		healthToDie = (int) (LIFE_EXPECTANCY + climat.difficulty * LIFE_EXPECTANCY);
	}

	@Override
	public Villager makeBaby(Villager villager) {
		Village village = villager.getVillage();
			
		
		if(!dude){//girl
			//if less than the maximum amount of babies
			if(kids <= (MAX_BABIES * climat.getRessources())){
				if(health > 18 && health < MAX_HEALTH_FOR_BABIES){//18 - 60
					if(babyCoolDown == 0){					//wants kids
						if(village.getPopulation() > 2){	//people in town
							int x = 0 + (int) (Math.random() * (100 - 0));
							if(x > (100) - 25){						//25% chance making baby
								Villager baby = new Villager(village, Random.randBool());

								AlgoMalthus algo = (AlgoMalthus) baby.algo;
								algo.health = 0;

								kids++;
								babyCoolDown = 1 + (int) (Math.random() * 
										((MAX_TIME_TO_MAKE_BABY * climat.getRessources()) 
												- 1));
								
								//Check de la pop max
								if(village.getPopulation() >= village.getMAX_POP()){
									
									int prob = (int) Math.random()*100;
									
									// 10 %
									if(prob < 10){
										climat.ressources -= (PAS * Math.abs(village.getMAX_POP() - village.getPopulation()));
										System.out.println("MOINS "
												+ (climat.getRessources()-PAS));
									}
									
								}
								else if (village.getPopulation() <= 2500){
									if(climat.ressources < village.maxRessource){
										System.out.println("PLUS " 
												+ (climat.getRessources()-PAS));
										climat.ressources += (PAS * village.getPopulation());
									}
								}
								
								return baby;
							}
						}
					}
				}
			}
		}

		return null;
	}

	@Override
	public boolean updateHealth(Villager villager) {

		Village village = villager.getVillage();

		health++;

		//System.out.println(health + " pouet : " + healthToDie);
		/*
		int x = health + (int) (Math.random() * 
				((LIFE_EXPECTANCY*village.climat.difficulty) 
						- health));
		 */
		if(health < healthToDie){
			int x = (int) (Math.random() * 100);

			if(x < 2){
				return true;
			}
		}

		if(health >= healthToDie){
			int x = (int) (Math.random() * 100);

			if(x < 80){
				return true;
			}
		}
		if(!dude){
			if(babyCoolDown > 0){
				babyCoolDown--;
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "AlgoBase [health=" + health + ", dude=" + dude
		+ ", babyCoolDown=" + babyCoolDown + ", kids=" + kids + "]";
	}
}
