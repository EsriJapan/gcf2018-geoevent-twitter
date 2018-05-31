package com.esrij.geoevent.solutions.processor.textanalysis;

public enum Part {

	VERB,
	NOUN,
	ADJECTIVE,
	ADVERB;

	public static Part createFromJapanese(String tmpName) {
		Part ret = null;
		if("動詞".equalsIgnoreCase(tmpName)) {
			ret = VERB;
		} else if("名詞".equalsIgnoreCase(tmpName)) {
			ret = NOUN;
		} else if("形容詞".equalsIgnoreCase(tmpName)) {
			ret = ADJECTIVE;
		} else if("副詞".equalsIgnoreCase(tmpName)) {
			ret = ADVERB;
		}
		return ret;
	}

	public static Part createFromEnglish(String tmpName) {
		Part ret = null;
		if("v".equalsIgnoreCase(tmpName)) {
			ret = VERB;
		} else if("n".equalsIgnoreCase(tmpName)) {
			ret = NOUN;
		} else if("a".equalsIgnoreCase(tmpName)) {
			ret = ADJECTIVE;
		} else if("r".equalsIgnoreCase(tmpName)) {
			ret = ADVERB;
		}
		return ret;
	}

}
