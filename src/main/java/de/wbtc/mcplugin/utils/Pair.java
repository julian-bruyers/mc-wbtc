/**
 * The Pair class is a simple generic class that holds two values of different types.
 *
 * @author Julian Bruyers
 */
package de.wbtc.mcplugin.utils;


/**
 * A simple generic class that holds two values of different types.
 *
 * @param <F> the type of the first value
 * @param <S> the type of the second value
 */
public class Pair<F, S> {
    private final F first;
    private final S second;

    /**
     * Constructs a new Pair with the specified values.
     *
     * @param first  the first value
     * @param second the second value
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first value of the pair.
     *
     * @return the first value
     */
    public F getFirst() {
        return first;
    }

    /**
     * Returns the second value of the pair.
     *
     * @return the second value
     */
    public S getSecond() {
        return second;
    }
}