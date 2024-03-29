package com.synex.domain;

import jakarta.persistence.*;

@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    
    private String status;
    
    private String claimStatus;
    
    public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	private Long userId;
    
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private Integer age;

    private Integer drivingRecord;

    private Integer vehicleValue;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    
    @Override
	public String toString() {
		return "Driver [id=" + id + ", name=" + name + ", status=" + status + ", claimStatus=" + claimStatus
				+ ", userId=" + userId + ", age=" + age + ", drivingRecord=" + drivingRecord + ", vehicleValue="
				+ vehicleValue + ", address=" + address + ", insurancePlan=" + insurancePlan + ", vehicle=" + vehicle
				+ "]";
	}

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "insurancePlan_id", referencedColumnName = "insurancePlanId")
    private InsurancePlan insurancePlan;
    
    public InsurancePlan getInsurancePlan() {
		return insurancePlan;
	}

	public void setInsurancePlan(InsurancePlan insurancePlan) {
		this.insurancePlan = insurancePlan;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;
    
    

    // Constructors, getters and setters...

    public Driver() {}

    public Driver(String name, Integer age, Integer drivingRecord, Integer vehicleValue) {
        this.name = name;
        this.age = age;
        this.drivingRecord = drivingRecord;
        this.vehicleValue = vehicleValue;
    }

    // Getters and setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDrivingRecord() {
        return drivingRecord;
    }

    public void setDrivingRecord(Integer drivingRecord) {
        this.drivingRecord = drivingRecord;
    }

    public Integer getVehicleValue() {
        return vehicleValue;
    }

    public void setVehicleValue(Integer vehicleValue) {
        this.vehicleValue = vehicleValue;
    }

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}