package com.abwilkinson.demo.service;

import com.abwilkinson.demo.domain.AgeRange;
import com.abwilkinson.demo.domain.Athlete;
import com.abwilkinson.demo.dto.AthleteResponse;
import com.abwilkinson.demo.dto.AthleteSearchRequest;
import com.abwilkinson.demo.repository.AthleteRepository;
import com.abwilkinson.demo.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AthleteServiceTest {
    @Mock
    private AthleteRepository athleteRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private AthleteService athleteService;

    @Test
    void testSearchAthletes_noParameters() {
        AthleteSearchRequest  request = new AthleteSearchRequest(null, null, null, 0);
        Athlete athlete = createAthlete("An Athlete");
        when(athleteRepository.findAll(ArgumentMatchers.<Specification<Athlete>>any())).thenReturn(List.of(athlete));

        List<AthleteResponse> responseList = athleteService.searchAthletes(request);

        assertEquals(1, responseList.size());
        verifyNoInteractions(skillRepository);
    }
    @Test
    void testSearchAthletes_emptySkills() {
        AthleteSearchRequest  request = new AthleteSearchRequest(null, null, Collections.emptyList(), 0);
        Athlete athlete = createAthlete("An Athlete");
        when(athleteRepository.findAll(ArgumentMatchers.<Specification<Athlete>>any())).thenReturn(List.of(athlete));

        List<AthleteResponse> responseList = athleteService.searchAthletes(request);

        assertEquals(1, responseList.size());
        verifyNoInteractions(skillRepository);
    }


    @Test
    void testSearchAthletes_skillsAreChecked() {
        List<String> skills = List.of("test");
        AthleteSearchRequest  request = new AthleteSearchRequest(null, null, skills, 0);

        List<AthleteResponse> responseList = athleteService.searchAthletes(request);

        assertEquals(0, responseList.size());
        verify(skillRepository, times(1)).findSkillHierarchyIds(skills);
        verifyNoInteractions(athleteRepository);
    }


    private Athlete createAthlete(String name) {
        Athlete athlete1 = new Athlete();
        athlete1.setName(name);
        return athlete1;
    }
}
