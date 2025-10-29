package br.com.fiap.universidade_fiap.control;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.universidade_fiap.service.ActuatorService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ActuatorController {

	private final RestTemplate template = new RestTemplate();
	
	@Autowired
	private ActuatorService actuatorService;
	
	@GetMapping("/controle/telemetria2")
	public ModelAndView retornarTelemetria2(HttpServletRequest req) {
		
		ModelAndView mv = new ModelAndView("/controle/telemetria2");
		mv.addObject("uri", req.getRequestURI());
		mv.addObject("cpu_usage", actuatorService.getCPUUsage());
		mv.addObject("health", actuatorService.getHealth());
		mv.addObject("jvm_used", actuatorService.getJvmMemoryUsed());
		mv.addObject("jvm_max", actuatorService.getJvmMemoryMax());
		mv.addObject("uptime_min", actuatorService.getProcessUptimeMin());
		mv.addObject("uptime_horas", actuatorService.getProcessUptimeHoras());
		return mv;
		
	}

	@GetMapping("/controle/telemetria")
	public ModelAndView retornarTelemetria(HttpServletRequest req) {
		// health
		// system.cpu.usage (%)
		// jvm.memory.used (MB)
		// jvm.memory.max (MB)
		ModelAndView mv = new ModelAndView("/controle/telemetria");

		Map health = template.getForObject("http://localhost:8080/actuator/health", Map.class);
		
		Map cpu_usage = template.getForObject("http://localhost:8080/actuator/metrics/system.cpu.usage", Map.class);
		
		List<Map<String,Object>> list_cpu_usage = ((List<Map<String,Object>>) cpu_usage.get("measurements"));
		Double double_cpu_usage = ((Number) list_cpu_usage.get(0).get("value")).doubleValue();		
		
		mv.addObject("cpu_usage", new DecimalFormat("#.##").format(double_cpu_usage * 100));
		
		Map jvm_used = template.getForObject("http://localhost:8080/actuator/metrics/jvm.memory.used", Map.class);
		
		List<Map<String,Object>> list_jvm_used = ((List<Map<String,Object>>) jvm_used.get("measurements"));
		Double double_jvm_used = ((Number) list_jvm_used.get(0).get("value")).doubleValue();		
		
		Map jvm_max = template.getForObject("http://localhost:8080/actuator/metrics/jvm.memory.max", Map.class);
		
		List<Map<String,Object>> list_jvm_max = ((List<Map<String,Object>>) jvm_max.get("measurements"));
		Double double_jvm_max = ((Number) list_jvm_max.get(0).get("value")).doubleValue();		

		mv.addObject("health", health);
		
		mv.addObject("jvm_used", new DecimalFormat("#.##").format(double_jvm_used / (1024 * 1024)));
		mv.addObject("jvm_max", new DecimalFormat("#.##").format(double_jvm_max / (1024 * 1024 * 1024)));
		mv.addObject("uri", req.getRequestURI());

		return mv;

	}

}
