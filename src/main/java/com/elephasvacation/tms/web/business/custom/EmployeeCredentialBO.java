package com.elephasvacation.tms.web.business.custom;

import com.elephasvacation.tms.web.business.SuperBO;
import com.elephasvacation.tms.web.dto.EmployeeCredentialDTO;

import java.util.List;

public interface EmployeeCredentialBO extends SuperBO {

    Integer createEmployeeCredential(EmployeeCredentialDTO employeeCredentialDTO) throws Exception;

    void updateEmployeeCredential(EmployeeCredentialDTO employeeCredentialDTO) throws Exception;

    void deleteEmployeeCredential(Integer employeeCredentialID) throws Exception;

    EmployeeCredentialDTO getEmployeeCredentialByID(Integer employeeCredentialID) throws Exception;

    List<EmployeeCredentialDTO> getAllEmployeeCredentials(Integer employeeCredentialID) throws Exception;
}
