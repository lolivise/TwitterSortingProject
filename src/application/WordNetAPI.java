package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class WordNetAPI {
	
	private URL url;
	private IDictionary dict;
	private List<String> synonymList;
	private List<String> antonymList;
	private List<String> hypernymList;
	private IIndexWord idxWord;
	private List<POS> pos;
	
	public WordNetAPI() throws MalformedURLException, IOException{
		synonymList = new ArrayList<>();
		antonymList = new ArrayList<>();
		hypernymList = new ArrayList<>();
		pos = new ArrayList<>();
		pos.add(POS.NOUN);
		pos.add(POS.VERB);
		pos.add(POS.ADJECTIVE);
		pos.add(POS.ADVERB);
		
		url = new URL("file", null,"D:\\Project\\TwitterProject\\Extra_Resource\\WordNet(JWI)\\dict\\");
		dict = new Dictionary(url);
		dict.open();
	}
	
	public boolean isExist(String word){
		for(POS type:pos){
			getIndexWord(word, type);
			if(idxWord!= null){
				return true;
			}
		}
		return false;
	}
	
	public List<String> getSynonymList(String originWord){
		synonymList.clear();
		for(POS type:pos){
			getIndexWord(originWord, type);
			//System.out.println(idxWord);
			if(idxWord!=null)
			for(IWordID wordID: idxWord.getWordIDs()){
				IWord word = dict.getWord(wordID);
				ISynset synset = word.getSynset();
				for(IWord w: synset.getWords()){
					String tempWord = w.getLemma().replaceAll("[,;:.?!'_-]"," ");
					if(!synonymList.contains(tempWord)){
						synonymList.add(tempWord);
					}
				}
			}
		}
		return synonymList;
	}
	
	public void getAntonymList(String originWord){
		antonymList.clear();
		for(POS type:pos){
			getIndexWord(originWord, type);
			if(idxWord!=null)
			for(IWordID wordID: idxWord.getWordIDs()){
				IWord word = dict.getWord(wordID);
				ISynset synset = word.getSynset();
				Map<IPointer, List<ISynsetID>> map = synset.getRelatedMap();
				List<ISynsetID> ISynsetIDList = map.get(Pointer.ANTONYM);
				for(ISynsetID sid: ISynsetIDList){
					List<IWord> words = dict.getSynset(sid).getWords();
					for(IWord iW: words){
						String antonym = iW.getLemma().replaceAll("[,;:.?!'_-]"," ");
						if(!antonymList.contains(antonym)){
							antonymList.add(antonym);
						}
					}
				}
				
			}
		}
			
	}
	
	public List<String> getHypernymList(String originWord){
		hypernymList.clear();
		for(POS type:pos){
			getIndexWord(originWord, type);
			if(idxWord!=null)
			for(IWordID wordID: idxWord.getWordIDs()){
				IWord word = dict.getWord(wordID);
				ISynset synset = word.getSynset();
				List<ISynsetID> ISynsetIDList = synset.getRelatedSynsets(Pointer.HYPERNYM);
				for(ISynsetID sID: ISynsetIDList){
					List<IWord> words = dict.getSynset(sID).getWords();
					for(IWord iW: words){
						String hypernym = iW.getLemma().replaceAll("[,;:.?!'_-]"," ");
						if(!hypernymList.contains(hypernym)){
							hypernymList.add(hypernym);
						}
					}
				}
			}
		}
		return hypernymList;
	}
	
	
	
	private void getIndexWord(String word, POS type){
		idxWord = dict.getIndexWord(word, type);
	}
	
}
