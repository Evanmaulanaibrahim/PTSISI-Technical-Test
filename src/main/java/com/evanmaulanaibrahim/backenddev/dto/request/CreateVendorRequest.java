package com.evanmaulanaibrahim.backenddev.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVendorRequest {
    @NotBlank(message = "Kolom nama lengkap tidak boleh kosong")
    @Size(max = 255, message = "Format nama lengkap belum sesuai. (Tidak menggunakan special character dan maksimal 255 karakter)")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Format nama lengkap belum sesuai. (Tidak menggunakan special character dan maksimal 255 karakter)")
    private String vendorName;

    @NotBlank(message = "Kolom alamat tidak boleh kosong")
    @Size(max = 500, message = "Alamat maksimal 500 karakter.")
    private String vendorAddress;

    @NotBlank(message = "Kolom nomor telepon tidak boleh kosong")
    @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Format nomor telepon tidak sesuai")
    @Size(max = 20, message = "Nomor telepon maksimal 20 karakter")
    private String vendorPhone;

    @NotBlank(message = "Kolom email tidak boleh kosong")
    @Email(message = "Format email tidak sesuai")
    @Size(max = 100, message = "Email maksimal 100 karakter")
    private String vendorEmail;
}
