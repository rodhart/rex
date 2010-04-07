package edu.udel.cis.cisc475.rex.generate.impl;

import edu.udel.cis.cisc475.rex.config.IF.ConfigIF;
import edu.udel.cis.cisc475.rex.exam.IF.ExamIF;
import edu.udel.cis.cisc475.rex.generate.IF.GeneratorIF;
import edu.udel.cis.cisc475.rex.key.IF.AnswerKeyIF;

public class Generator implements GeneratorIF {

	private ExamIF master;

	private ConfigIF config;

	private AnswerKeyIF[] keys;

	private ExamIF[] generatedExams;

	Generator(ExamIF master, ConfigIF config) {
		this.master = master;
		this.config = config;
		generate();
	}

	private void generate() {
		// here's where the real work gets done...
	}

	@Override
	public AnswerKeyIF getAnswerKey(int i) {
		return keys[i];
	}

	@Override
	public ConfigIF getConfig() {
		return config;
	}

	@Override
	public ExamIF getGeneratedExam(int i) {
		return generatedExams[i];
	}

	@Override
	public ExamIF getMaster() {
		return master;
	}

	@Override
	public int numGeneratedExams() {
		return config.numVersions(); // CHECK THIS!!!
	}

}
