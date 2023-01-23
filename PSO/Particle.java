package com.PSO;

import java.util.Random;

/**
 * @author beline
 * @param <N>
 *     each particle has the value of her position, we'll change that value into steps of movement,
 *     to reach the final state.
 *     each particle is defined by a velocity, fitness and local optimal.
 *     we'll calculate the fitness value using a heuristic method.
 */

public class Particle<N extends Comparable<N>> implements Comparable<Particle<N>> {

    private State finalState;
    private double fitness; //f(x,y)
    private double pBest; // local optimal
    private double position; // X
    private double velocity; // V

    /**
     * Initial particle
     * @param finaleState
     * @param position
     * @param fitness
     * @param velocity
     */
    public Particle(State finaleState, double position, double fitness, double velocity){
        this.finalState = finaleState;
        this.position = position;
        this.fitness = fitness;
        this.velocity=velocity;
        calculatePBest();
    }

    /**
     * if the value of fitness is not equal to 0, the program will continue to update
     * the local optimal, the velocity and the position of the particle
     */

    // calculate the new value of the local optimal
    public void calculatePBest(){this.pBest=this.position;}

    /**
     * calculate the new value of the velocity
     * @param gBest
     * @param c1
     * @param c2
     * @param w
     */
    public void calculateVelocity(double gBest,
                                  double c1,
                                  double c2,
                                  double w){
        Random rand = new Random();
        double r1=rand.nextDouble();
        double r2=rand.nextDouble();
        this.velocity=w*this.velocity + c1*r1*(this.pBest-this.position)
        +c2*r2*(gBest-this.position);
    }

    // calculate the new value of the velocity
    public void calculatePosition(){this.position=this.position+this.velocity;}

    /**
     * Update the velocity, position and the local optimal
     * @param gBest
     * @param c1
     * @param c2
     * @param w
     */
    public void update(double gBest,
                       double c1,
                       double c2,
                       double w){
        calculateVelocity(gBest,c1, c2,w);
        calculatePosition();
        calculatePBest();
    }


    public State getFinalState() {return finalState;}
    public double getFitness() {return fitness;}
    public double getPBest() {return pBest;}
    public double getPosition() {return position;}
    public double getVelocity() {return velocity;}
    public void setFinalState(State finalState){this.finalState=finalState;}
    public void setFitness(double fitness){this.fitness=fitness;}


    /**
     *
     * @param nParticle
     * the program will use this method to compare the fitness of each particle, and take the minimum cost
     * @return
     */
    @Override
    public int compareTo(Particle<N> nParticle) {
        int compare = Double.compare(this.fitness, nParticle.getFitness());
        if (compare == 0)
        return 0;
        else return this.fitness>nParticle.getFitness() ? 1:-1;
    }

    public void display(){
        this.getFinalState().display();
        System.out.println("the velocity: "+getVelocity());
        System.out.println("the position value: "+getPosition());
        System.out.println("the local best: "+getPBest());
        System.out.println("the fitness value: "+getFitness());
    }
}
