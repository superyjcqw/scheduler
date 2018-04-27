package com.gk.scheduler.jobs;

import com.gk.scheduler.base.ProxyBaseJob;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TestJob extends ProxyBaseJob {

	@Override
	public void execute() {
		System.out.println(new Date());
	}

}