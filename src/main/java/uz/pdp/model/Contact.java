package uz.pdp.model;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "phone")
//@ToString
public class Contact {
    private String fullName;
    private String phone;

    @Override
    public String toString() {
        return fullName+"    : "+phone;
    }
}
