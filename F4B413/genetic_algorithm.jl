immutable Component
    min::Float64
    max::Float64
    bits::Int
end

function decode(chromosome, components)
    cur = 1
    out = zeros(length(components))
    
    for i = 1:length(components)
        c = components[i]
        delta = (c.max - c.min) / (2^c.bits)
        value = c.min + parse(Int, chromosome[cur:cur+c.bits-1], 2) * delta
        cur += c.bits
        out[i] = value
    end
    
    return out
end

function genalg(components, iterations, population_size, crossover_rate, mutation_rate)
    # Size of the whole genome
    gs = mapreduce(c -> c.bits, +, components)

    # Number of genes
    ng = length(components)

    # solution
    solution = []
    
    # Generate initial population
    population = []
    for i = 1:population_size
        push!(population, join(rand(['0', '1'], gs)))
    end
    
    for i = 1:iterations
        # Evaluate individuals
        evaluations = zeros(population_size)
        for j = 1:population_size
            evaluations[j] = evaluate(decode(population[j], components))
        end
    
        # Save the best individual
        best_index = indmax(evaluations)
        best_individual = population[best_index]

        # Selection by evaluation
        probabilities = evaluations / sum(evaluations)
        pool = []
        z = 0

        while size(pool, 1) < population_size
            k = (z % population_size) + 1
            if rand() < probabilities[k]
                push!(pool, population[k]) 
            end
            z += 1
        end
        
        # Crossover two-by-two
        for j = 0:Int(population_size/2)-1
            parent1 = pool[2*j+1]
            parent2 = pool[2*j+2]
            
            # Cut in the middle
            ind = round(Int, 0.5*gs)
            child1 = join([parent1[1:ind] parent2[ind+1:gs]])
            child2 = join([parent2[1:ind] parent1[ind+1:gs]])
            
            # Insert in population depending on crossover rate
            if rand() < crossover_rate
                population[2*j+1] = child1
                population[2*j+2] = child1
            else
                population[2*j+1] = parent1
                population[2*j+2] = parent1
            end
            
            # Mutate
            for k = 1:2
                cur = 1
                for c = components
                    if rand() < mutation_rate
                        # Flip random bit
                        # TODO: Optimize
                        p = cur + round(Int, (c.bits - 1) * rand())
                        arr = read(IOBuffer(population[2*j+k]))
                        arr[p] = arr[p] == 0x30 ? 0x31 : 0x30
                        population[2*j+k] = convert(String, arr)
                    end
                    cur += c.bits
                end
            end
        end
        
        # Re-inject best
        population[best_index] = best_individual;

        # Keep best fitness
        # fit_history(:,i) = ratings;

        solution = decode(best_individual, components);
    end

    return solution
end

function evaluate(solution)
    return 100 + sum(solution)
end

components = [Component(-10, 10, 32), Component(-2.1, 3.8, 32), Component(-50, 20, 32)]

# Run once for compilation
genalg(components, 100, 100, 0.9, 0.2)

start = time()
solution = genalg(components, 1000, 100, 0.9, 0.05)
println(time() - start)

println(solution)
