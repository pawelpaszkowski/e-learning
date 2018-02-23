package pl.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="access")
public class Access {
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idaccess")
    private Long idaccess;
	
	@Column(name="userid")
	private Long userid;
	@Column(name="roleid")
	private Long roleid;
	@Column(name="subjectid")
	private Long subjectid;
	
	public Access() {}
	
	public Access(Long userid, Long roleid, Long subjectid) {
		super();
		this.userid = userid;
		this.roleid = roleid;
		this.subjectid = subjectid;
	}
	
	public Access(Long idaccess, Long userid, Long roleid, Long subjectid) {
		super();
		this.idaccess = idaccess;
		this.userid = userid;
		this.roleid = roleid;
		this.subjectid = subjectid;
	}
	public Long getIdaccess() {
		return idaccess;
	}
	public void setIdaccess(Long idaccess) {
		this.idaccess = idaccess;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getRoleid() {
		return roleid;
	}
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	public Long getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(Long subjectid) {
		this.subjectid = subjectid;
	}
	@Override
	public String toString() {
		return "Access [idaccess=" + idaccess + ", userid=" + userid + ", roleid=" + roleid + ", subjectid=" + subjectid
				+ "]";
	}
	
	
	
}
