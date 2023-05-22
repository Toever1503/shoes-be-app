package com.photoism.cms.domain.admin;

import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.domain.admin.dto.PrivilegeReqDto;
import com.photoism.cms.domain.admin.dto.PrivilegeResDto;
import com.photoism.cms.domain.admin.dto.RoleResDto;
import com.photoism.cms.domain.auth.entity.PrivilegeEntity;
import com.photoism.cms.domain.auth.repository.PrivilegeQueryRepository;
import com.photoism.cms.domain.auth.repository.PrivilegeRepository;
import com.photoism.cms.domain.etc.entity.CodeEntity;
import com.photoism.cms.domain.etc.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final CodeRepository codeRepository;
    private final PrivilegeQueryRepository privilegeQueryRepository;
    private final PrivilegeRepository privilegeRepository;

    @Transactional(readOnly = true)
    public List<RoleResDto> getRoles() {
        List<RoleResDto> resDtoList = new ArrayList<>();
        List<CodeEntity> roleList = codeRepository.findByCodeGroupOrderByPositionAsc("ROLE");
        for (CodeEntity codeEntity : roleList) {
            RoleResDto roleResDto = new RoleResDto(codeEntity.getCode(), codeEntity.getNameKr(), codeEntity.getNameEn());
            List<PrivilegeResDto> privileges = privilegeQueryRepository.getPrivileges(codeEntity.getCode());
            roleResDto.setPrivileges(privileges);
            resDtoList.add(roleResDto);
        }
        return resDtoList;
    }

    @Transactional
    public void updateRoles(List<PrivilegeReqDto> reqDtoList) {
        reqDtoList.forEach(privilegeReqDto -> {
            CodeEntity roleCode = codeRepository.findByCode(privilegeReqDto.getRoleCd()).orElseThrow(() -> new ObjectNotFoundException("role"));
            if (!roleCode.getCodeGroup().equals("ROLE"))
                throw new ObjectNotFoundException("role");

            List<PrivilegeEntity> oldPrivileges = privilegeRepository.findByRoleCd(roleCode.getCode());
            privilegeRepository.deleteAll(oldPrivileges);
            privilegeRepository.flush();

            privilegeReqDto.getPrivileges().forEach(privilege -> {
                CodeEntity privilegeCode = codeRepository.findByCode(privilege).orElseThrow(() -> new ObjectNotFoundException("privilege"));
                if (!privilegeCode.getCodeGroup().equals("PRIVILEGE"))
                    throw new ObjectNotFoundException("privilege");

                PrivilegeEntity newPrivilege = PrivilegeEntity.builder()
                        .roleCd(roleCode.getCode())
                        .privilegeCd(privilegeCode.getCode())
                        .build();
                privilegeRepository.save(newPrivilege);
            });
        });
    }
}
