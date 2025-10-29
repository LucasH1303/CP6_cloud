package br.com.fiap.universidade_fiap.service;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

@Service
public class ActuatorService {
	
	@Autowired
	private MeterRegistry registry;
	
	@Autowired
	private HealthEndpoint healthEndpoint;
	
	public String getJvmMemoryUsed() {
		double jvmUsedMemory = registry.get("jvm.memory.used").gauge().value();
		return  new DecimalFormat("#.##").format(jvmUsedMemory / (1024 * 1024));
	}
	
	public String getJvmMemoryMax() {
		double jvmMemoryMax = registry.get("jvm.memory.max").gauge().value();
		return new DecimalFormat("#.##").format(jvmMemoryMax / (1024 * 1024 * 1024));
	}
	
	public String getCPUUsage() {
		double cpuUsage = registry.get("system.cpu.usage").gauge().value();
		return new DecimalFormat("#.##").format(cpuUsage * 100);
		
	}
	
	public String getProcessUptimeMin() {
		double processUptime = registry.get("process.uptime").gauge().value();
		return new DecimalFormat("#.##").format(processUptime/60);
	}
	
	public String getProcessUptimeHoras() {
		double processUptime = registry.get("process.uptime").gauge().value();
		return new DecimalFormat("#.##").format(processUptime/3600);
	}
	
	public HealthComponent getHealth() {
		return healthEndpoint.health();
	}
	
	

}
