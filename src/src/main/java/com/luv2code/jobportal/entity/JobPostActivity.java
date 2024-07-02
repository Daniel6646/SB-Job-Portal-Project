package com.luv2code.jobportal.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class JobPostActivity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer jobPostId;
	
	@ManyToOne
	@JoinColumn(name =  "postedById", referencedColumnName = "userId")
	private Users postedById;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "jobLocationId", referencedColumnName = "Id")
	private JobLocation jobLocation;
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "jobCompanyId", referencedColumnName = "Id")
	private JobCompany jobcompanyId;
	
	@Transient
	private boolean isActive;
	
	@Length(max = 10000) 
	private String descriptionOfJob;
	
	private String jobType;
	
	private String salary;
	
	private String remote;

	@DateTimeFormat(pattern =" dd-MM-yyyy")
	private Date postedDate;

	private String jobTitle;

	public JobPostActivity() {
		
	}
	
	public JobPostActivity(Integer jobPostId, Users postedById, JobLocation jobLocation, JobCompany jobcompanyId,
			boolean isActive, @Length(max = 10000) String descriptionOfJob, String jobType, String salary,
			String remote, Date postedDate, String jobTitle) {
		super();
		this.jobPostId = jobPostId;
		this.postedById = postedById;
		this.jobLocation = jobLocation;
		this.jobcompanyId = jobcompanyId;
		this.isActive = isActive;
		this.descriptionOfJob = descriptionOfJob;
		this.jobType = jobType;
		this.salary = salary;
		this.remote = remote;
		this.postedDate = postedDate;
		this.jobTitle = jobTitle;
	}

	public Integer getJobPostId() {
		return jobPostId;
	}

	public void setJobPostId(Integer jobPostId) {
		this.jobPostId = jobPostId;
	}

	public Users getPostedById() {
		return postedById;
	}

	public void setPostedById(Users postedById) {
		this.postedById = postedById;
	}

	public JobLocation getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(JobLocation jobLocation) {
		this.jobLocation = jobLocation;
	}

	public JobCompany getJobcompanyId() {
		return jobcompanyId;
	}

	public void setJobcompanyId(JobCompany jobcompanyId) {
		this.jobcompanyId = jobcompanyId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getDescriptionOfJob() {
		return descriptionOfJob;
	}

	public void setDescriptionOfJob(String descriptionOfJob) {
		this.descriptionOfJob = descriptionOfJob;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getRemote() {
		return remote;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Override
	public String toString() {
		return "JobPostActivity [jobPostId=" + jobPostId + ", postedById=" + postedById + ", jobLocation=" + jobLocation
				+ ", jobcompanyId=" + jobcompanyId + ", isActive=" + isActive + ", descriptionOfJob=" + descriptionOfJob
				+ ", jobType=" + jobType + ", salary=" + salary + ", remote=" + remote + ", postedDate=" + postedDate
				+ ", jobTitle=" + jobTitle + "]";
	}
	
	
	
	
	
}
