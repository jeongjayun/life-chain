package com.mysite.sbb.manual.service;

import java.util.List;
import java.util.Optional;

import com.mysite.sbb.manual.entity.Manual;
import org.springframework.stereotype.Service;

import com.mysite.sbb.common.DataNotFoundException;
import com.mysite.sbb.manual.entity.ManualContent;
import com.mysite.sbb.manual.repository.ManualContentRepository;
import com.mysite.sbb.manual.repository.ManualRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManualContentService {
	
	private final ManualRepository manualRepository;
	private final ManualContentRepository manualContentRepository;
	
	public List<ManualContent> getList(Manual manual) {
		return this.manualContentRepository.findAllByManual(manual);
	}
	
	public List<ManualContent> getManualList(ManualContent manualContent) {
		return manualContentRepository.findByManual(manualContent);
	}
	
	public List<ManualContent> getManualContentList() {
	    return manualContentRepository.findAll();
	}
	
	public List<ManualContent> getManualContentWithManual() {
	    return manualContentRepository.findManualContentWithManual();
	}
	
	
	
	public Manual getManual(Integer id) {
		Optional<Manual> manual = this.manualRepository.findById(id);
		if(manual.isPresent()) {
			return manual.get();
		} else {
			throw new DataNotFoundException("manual not found");
		}
	}

}
