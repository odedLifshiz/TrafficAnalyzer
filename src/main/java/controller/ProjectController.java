package controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.validation.Valid;

import model.Capture;
import model.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import repository.ProjectRepository;
import service.CaptureService;
import service.ProjectService;

@Controller
@RequestMapping(value="/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CaptureService captureService;
		
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newProjectPage() {
		ModelAndView mav = new ModelAndView("project-new", "project", new Project());
		return mav;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewProject(@ModelAttribute @Valid Project project,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("project-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New project " + project.getName() + " was successfully created.";
		
		
		projectService.create(project);
		mav.setViewName("redirect:/project/list.html");
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView projectListPage() {
		ModelAndView mav = new ModelAndView("project-list");
		List<Project> projectList = projectService.findAll();
		mav.addObject("projectList", projectList);
		return mav;
	}
	
	@RequestMapping(value="/view/{id}", method=RequestMethod.GET)
	public ModelAndView viewProjectPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("project-view");
		Project project = projectService.findById(id);
		mav.addObject("project", project);
		mav.addObject("size", project.getCaptures().size());
		mav.addObject("captureList", project.getCaptures());
		return mav;
	}	
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editProjectPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("project-edit");
		Project project = projectService.findById(id);
		mav.addObject("project", project);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editProject(@ModelAttribute @Valid Project project,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes)  {
		
		if (result.hasErrors()) {
			return new ModelAndView("project-edit");
		}
		
		ModelAndView mav = new ModelAndView("redirect:/project/list.html");
		String message = "Project was successfully updated.";
		try {
			Project projectToEdit = projectService.findById(id);
			projectToEdit.setName(project.getName());
			projectService.updateProject(projectToEdit);
		}
		catch(Exception e){
			 message = "Could not update project: " + project.getName() + ". Error was: " + e.getStackTrace();
		}
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteProject(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes)  {
		ModelAndView mav = new ModelAndView("redirect:/project/list.html");
		String message = null;
		Project project =null;
		try {
			project = projectService.findById(id);
			projectService.delete(id);
			message = "Project: " + project.getName() + " was successfully deleted.";
		}
		catch (Exception e){
			message = "could not delete project. error was: " + e.getStackTrace();
		}
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	
	@RequestMapping(value="/addCapture/{projectid}", method=RequestMethod.POST)
	public ModelAndView addCaptureToProject(
				@PathVariable Integer projectid, 
				@RequestParam("file") MultipartFile captureFile,
				final RedirectAttributes redirectAttributes
				)
	{
		
	
		ModelAndView mav = new ModelAndView("redirect:/project/view/" + projectid +".html");
		String message = "Capture " + captureFile.getOriginalFilename() + " was added successfully.";
			
		if(isValidCaptureFile(captureFile)) {
            try {
        		
                byte[] bytes = captureFile.getBytes();
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + projectid + "_projectFiles");
                if (!dir.exists())
                    dir.mkdirs();
                
                // Create the file on server
                File capturePathOnServer = new File(dir.getAbsolutePath()
                        + File.separator + captureFile.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(capturePathOnServer));
                stream.write(bytes);
                stream.close();
                Capture capture = null;
                try{
                	capture = new Capture();
	        		capture.setName(captureFile.getOriginalFilename());
	        		capture.setPath(capturePathOnServer.getAbsolutePath());
	        		Project project = projectService.findById(projectid);
	        		captureService.create(capture);
	        		project.addCapture(capture);
	        		projectService.updateProject(project);
                }catch(Exception e){
                	message = capture.toString() + ". Could not create capture. Exception was: " + e.getStackTrace().toString();
                }
        		
        		
            } catch (Exception e) {
            	message = "Could not upload capture. Exception was: " + e.getStackTrace();
            }
        } else {
        	message = "File is not a valid capture file.";
        }
		
		redirectAttributes.addFlashAttribute("message", message);
        return mav;

	}	
	
	private boolean isValidCaptureFile(MultipartFile captureFile) {
		if(captureFile.isEmpty()){
			return false;
		}
		
		return true;
	}
	
}
