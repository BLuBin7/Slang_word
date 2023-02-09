package Model;

import java.io.*;
import java.util.*;
import java.util.function.BooleanSupplier;

public class BidirectionalMap implements Serializable {
    /**
     * Key->value: Slang word -> definitions
     */
    private final TreeMap<String, List<String>> slangMap;
    /**
     * Value->key: Definition -> slang words
     */
    private final TreeMap<String, List<String>> definitionMap;

    /**
     * Create a new map
     */
    public BidirectionalMap()
    {
        //Slang -> definition
        slangMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        //Definition -> list of slang words
        definitionMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }
    private void addDefinitions(List<String> definitions, String slangWord)
    {
        for (String def : definitions)
        {
            //Add new slang to the new definition
            List<String> slangWords = definitionMap.computeIfAbsent(def, k -> new ArrayList<>());
            //If the slang words list does not exist
            slangWords.add(slangWord);
        }
    }

    public void addNotCheck(String slang, List<String> definition)
    {
        slangMap.put(slang, definition);
        addDefinitions(definition, slang);
    }

    private void deleteSlangWordFromDefinition(List<String> definitions, String slangWord)
    {
        for (String def : definitions)
        {
            List<String> slangList = definitionMap.get(def);
            if (slangList != null)
            {
                if (slangList.size() == 1)
                    //If there is no slang words of the definition -> remove the slang list
                    definitionMap.remove(def);
                else
                    //Remove the slang from the old definition
                    slangList.remove(slangWord);
            }
        }
    }

    public void update(SlangWord slangWord)
    {
        List<String> oldDefinition = slangMap.get(slangWord.word);
        //Add to replace the old definition
        slangMap.put(slangWord.word, slangWord.definition);
        //Remove from the slang list of the definitions
        deleteSlangWordFromDefinition(oldDefinition, slangWord.word);
        //Replace for each definition
        addDefinitions(slangWord.definition, slangWord.word);
    }

    public SlangWord random()
    {
        try
        {
            List<Map.Entry<String, List<String>>> pairs = slangMap.entrySet().stream().toList();
            //Random an index of the generated array
            int randIndex = new Random().nextInt(pairs.size());
            Map.Entry<String, List<String>> choice = pairs.get(randIndex);
            return new SlangWord(choice.getKey(), choice.getValue());
        } catch (Exception e)
        {
            return null;
        }
    }

    public void delete(String slangWord)
    {
        List<String> definition = slangMap.get(slangWord);
        //Remove from the slang map
        slangMap.remove(slangWord);
        //Remove from the slang list of the definitions
        deleteSlangWordFromDefinition(definition, slangWord);
    }


    /**
     * Clear the content of the map
     */
    public void clear()
    {
        slangMap.clear();
        definitionMap.clear();
    }


    /**
     * Load the data of the map from the original file (not structured)
     * @param path the path of loaded file
     */
    public void load(String path)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            //Ignore the first line
            reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                try
                {
                    String[] parts = line.split("`");
                    String[] defs = parts[1].split("\\|");
                    for (int i = 0; i < defs.length; i++)
                        defs[i] = defs[i].trim();
                    addNotCheck(parts[0], Arrays.stream(defs).toList());
                }
                catch (Exception e)
                {
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get the slang map (slang word -> definition)
     * @return the slang map
     */
    public Map<String, List<String>> getSlangMap() {
        return slangMap;
    }

    /**
     * Get the definition map (definition -> slang word)
     * @return the definition map
     */
    public Map<String, List<String>> getDefinitionMap() {
        return definitionMap;
    }
    public Question randomSlangQuestion()
    {
        List<Map.Entry<String, List<String>>> pairs = slangMap.entrySet().stream().toList();
        List<String> answers = new ArrayList<>(Question.NUMBER_OF_ANSWER);
        Random random = new Random();
        //Get indexes of the slang words used
        int[] randIndex = random.ints(Question.NUMBER_OF_ANSWER, 0, pairs.size()).toArray();
        //Choose the slang word for the user to guess
        int answerRandIndex = random.nextInt(randIndex.length);
        for (int i : randIndex)
        {
            List<String> curDefs = pairs.get(i).getValue();
            int randDefinitionEachSlang = random.nextInt(curDefs.size());
            answers.add(curDefs.get(randDefinitionEachSlang));
        }
        return new Question(pairs.get(randIndex[answerRandIndex]).getKey(), answers, answerRandIndex);
    }

    public Question randomDefinitionQuestion()
    {
        List<Map.Entry<String, List<String>>> pairs = definitionMap.entrySet().stream().toList();
        Random random = new Random();
        //Contains the slang words (answer of the question)
        List<String> answers = new ArrayList<>(Question.NUMBER_OF_ANSWER);
        //Get indexes of the definition used
        int[] randIndex = random.ints(Question.NUMBER_OF_ANSWER, 0, pairs.size()).toArray();
        for (int i : randIndex)
        {
            //For each definition
            //Get the slang word answered to the slang word
            List<String> curSlang = pairs.get(i).getValue();
            int randIndexForSlang = random.nextInt(curSlang.size());
            answers.add(curSlang.get(randIndexForSlang));
        }
        //Choose the slang word for the user to guess
        int answerRandIndex = random.nextInt(randIndex.length);
        return new Question(pairs.get(randIndex[answerRandIndex]).getKey(), answers, answerRandIndex);
    }



}
