package com.elephasvacation.tms.web.business.custom.impl;

import com.elephasvacation.tms.web.business.custom.EmployeeCredentialBO;
import com.elephasvacation.tms.web.business.custom.util.EmployeeCredentialDTOMapper;
import com.elephasvacation.tms.web.dal.DAOFactory;
import com.elephasvacation.tms.web.dal.DAOTypes;
import com.elephasvacation.tms.web.dal.custom.EmployeeCredentialDAO;
import com.elephasvacation.tms.web.dto.EmployeeCredentialDTO;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeCredentialBOImpl implements EmployeeCredentialBO {

    EmployeeCredentialDTOMapper mapper = EmployeeCredentialDTOMapper.instance;
    private EmployeeCredentialDAO employeeCredentialDAO = DAOFactory.getInstance()
            .getDAO(DAOTypes.EMPLOYEE_CREDENTIAL);
    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer createEmployeeCredential(EmployeeCredentialDTO employeeCredentialDTO) throws Exception {
        return this.employeeCredentialDAO.save(this.mapper.getEmployeeCredential(employeeCredentialDTO));
    }

    @Override
    public void updateEmployeeCredential(EmployeeCredentialDTO employeeCredentialDTO) throws Exception {
        this.employeeCredentialDAO.update(this.mapper.getEmployeeCredential(employeeCredentialDTO));
    }

    @Override
    public void deleteEmployeeCredential(Integer employeeCredentialID) throws Exception {
        this.employeeCredentialDAO.delete(employeeCredentialID);
    }

    @Override
    public EmployeeCredentialDTO getEmployeeCredentialByID(Integer employeeCredentialID) throws Exception {
        return this.mapper.getEmployeeCredentialDTO(this.employeeCredentialDAO.get(employeeCredentialID));
    }

    @Override
    public List<EmployeeCredentialDTO> getAllEmployeeCredentials(Integer employeeCredentialID) throws Exception {
        return this.mapper.getEmployeeCredentialDTOList(this.employeeCredentialDAO.getAll());
    }
}
