package org.kopacz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.kopacz.Kid;
import org.kopacz.Mother;

import java.util.List;

@Getter
@AllArgsConstructor
public class StartResponse {
    private List<Mother> mothers;
    private List<Kid> kids;
}
