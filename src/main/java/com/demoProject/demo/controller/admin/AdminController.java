package com.demoProject.demo.controller.admin;


import com.demoProject.demo.common.payload.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
//    private final AdminService adminService;
//
//    @PreAuthorize("hasAnyAuthority(@authorityConstant.ADMIN)")
//    @GetMapping("/dashboard")
//    public ResponseEntity<?> getDashboard() {
//        return ResponseEntity.ok(Response.ofSucceeded(adminService.getDashboard()));
//    }
//
//    @PreAuthorize("hasAnyAuthority(@authorityConstant.ADMIN)")
//    @PutMapping ("/force-return")
//    public ResponseEntity<?> forceReturn(
//            @RequestParam List<String> seatIds, Authentication connectedUser
//    ) {
//        adminService.forceReturn(seatIds,connectedUser);
//        return ResponseEntity.ok(Response.ofSucceeded("Force return successfully"));
//    }
//
//    @PreAuthorize("hasAnyAuthority(@authorityConstant.ADMIN)")
//    @PutMapping("/change-seat-status/{seatId}")
//    public ResponseEntity<?> changeSeatStatus(
//            @PathVariable("seatId") String seatId
//    ) {
//        adminService.changeSeatStatus(seatId);
//        return ResponseEntity.ok(Response.ofSucceeded("Change seat status successfully"));
//    }

}
