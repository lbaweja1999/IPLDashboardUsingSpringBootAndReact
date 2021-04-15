package com.ipl.dashboard.data;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.ipl.dashboard.models.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

	private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

	@Override
	public Match process(final MatchInput matchInput) throws Exception {

		// Set team 1 and team 2 depending on the innings order
		String firstInningsTeam = null; // team which batted first
		String secondInningsTeam = null; // team which batted second
		Match match = new Match();
		match.setId(Long.parseLong(matchInput.getId()));
		match.setCity(matchInput.getCity());
		match.setDate(LocalDate.parse(matchInput.getDate()));
		match.setPlayerOfMatch(matchInput.getPlayer_of_match());
		match.setVenue(matchInput.getVenue());

		if ("bat".equals(matchInput.getToss_decision())) {
			firstInningsTeam = matchInput.getToss_winner();
			if (matchInput.getTeam1().equals(firstInningsTeam)) {
				secondInningsTeam = matchInput.getTeam2();
			} else {
				secondInningsTeam = matchInput.getTeam1();
			}
		} else if ("field".equals(matchInput.getToss_decision())) {
			secondInningsTeam = matchInput.getToss_winner();
			if (matchInput.getTeam1().equals(secondInningsTeam)) {
				firstInningsTeam = matchInput.getTeam2();
			} else {
				firstInningsTeam = matchInput.getTeam1();
			}

		}
		match.setTeam1(firstInningsTeam);
		match.setTeam2(secondInningsTeam);
		match.setTossWinner(matchInput.getToss_winner());
		match.setPlayerOfMatch(matchInput.getPlayer_of_match());
		match.setResult(matchInput.getResult());
		match.setResultMargin(matchInput.getResult_margin());
		match.setTossDecision(matchInput.getToss_decision());
		match.setUmpire1(matchInput.getUmpire1());
		match.setUmpire2(matchInput.getUmpire2());
		return match;
	}

}