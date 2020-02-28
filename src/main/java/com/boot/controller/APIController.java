package com.boot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boot.model.City;
import com.boot.model.Nation;
import com.boot.model.Person;
import com.boot.repositories.NationRepository;
import com.boot.services.DataService;
import com.boot.services.PersonService;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	DataService dservice;
	
	@Autowired
	PersonService pservice;
	
	@Autowired
	NationRepository nrepo;
	
	@RequestMapping("/greet")
	public String hello() {
		return  "Hello!!!! Spring boot!!!";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/teams")
	public Map<String,String[]> teams() {
		return dservice.names();
	}
	
	@RequestMapping("/allcities")
	public List<City> getCities(){
		return dservice.getCity();
	}
	
	@RequestMapping("/allcities/{code}")
	public List<City> getCitiesByCode(@PathVariable("code")String code){
		return dservice.getCity(code);
	}
	
	@RequestMapping("/cities/{pattern}")
	public List<City> getCities(@PathVariable("pattern")String pattern){
		return dservice.getCities(pattern);
	}
	
	
	@RequestMapping("/cities")
	public List<City> getCities2(@PathParam("pattern")String pattern){
		return dservice.getCities(pattern);
	}
	
	@RequestMapping(value="/person",method = {RequestMethod.POST,RequestMethod.PUT})
	public ResponseEntity<Person> addPerson(@RequestBody Person person)
	{
		try {
		pservice.putPerson(person);
		return new ResponseEntity<Person>(person,HttpStatus.OK);
		}
		catch(Exception e) {
	    return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/person")
	public ResponseEntity<Person> deletePerson(@RequestBody Person person)
	{
		try {
		pservice.removePerson(person);
		return new ResponseEntity<Person>(person,HttpStatus.OK);
		}
		catch(Exception e) {
	    return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/person/{ID}")
	public ResponseEntity<Integer> deletePerson(@PathVariable("ID") Integer sno){
		try {
			pservice.removePerson(sno);
			return new ResponseEntity<Integer>(sno,HttpStatus.OK);
			}
			catch(Exception e) {
			e.printStackTrace();
		    return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
			}	
		
	}
	
	@RequestMapping("/countries")
	public List<Nation> getCountries(){
		return (List<Nation>)nrepo.findAll();
	}
	
	@RequestMapping(
			  value = "/images/{filename}",
			  produces = MediaType.IMAGE_JPEG_VALUE
			)
			public @ResponseBody byte[] getImageWithMediaType(@PathVariable String filename) throws IOException {
		     File f=new File("D:\\images\\"+filename);
			    FileInputStream in = new FileInputStream(f);
			    return IOUtils.toByteArray(in);
			}
	//form-data
	@PostMapping("/uploads/pics")
	 public ResponseEntity<String> addFile(@RequestParam("file") MultipartFile file)
	 {
		 if(file.isEmpty()){
			 return new ResponseEntity<String>("File not attached",HttpStatus.BAD_REQUEST);
		 }
		
		 else{
			 String allowed[]={"jpg","jpeg","png","gif"};
			 Boolean valid=false;
			 String extension=FilenameUtils.getExtension(file.getOriginalFilename());
			 for(String x:allowed){
				 if(x.contentEquals(extension)){
					 valid=true;
					 break;
				 } 
			 }
			 if(valid){
				 try {
					 byte b[]=file.getBytes();
					 Path p=Paths.get("d:\\images\\"+file.getOriginalFilename());
					 Files.write(p,b);
				 } 
				 catch (IOException e) {
				// TODO Auto-generated catch block
					 	e.printStackTrace();
					 	return new ResponseEntity<String>("Error in Writing",HttpStatus.INTERNAL_SERVER_ERROR);
				 }
				 
				 return new ResponseEntity<String>("File uploaded successfully",HttpStatus.OK);
			 }
			 
			 return new ResponseEntity<String>("Only png,jpeg,jpg and gif allowed",HttpStatus.BAD_REQUEST); 
		 }}
	
}
