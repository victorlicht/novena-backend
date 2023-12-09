package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.dtos.AppointmentDto;
import com.victorlicht.novenabackend.dtos.DoctorDto;
import com.victorlicht.novenabackend.mapper.AppointmentMapper;
import com.victorlicht.novenabackend.models.Appointment;
import com.victorlicht.novenabackend.models.Shift;
import com.victorlicht.novenabackend.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment createAppointment(AppointmentDto appointmentDto) {
         return appointmentRepository.save(AppointmentMapper.toEntity(appointmentDto));
    }

    @Override
    public boolean isDoctorWorking(DoctorDto doctorDto, Date date) {
        List<Shift> shifts = doctorDto.getShifts();
        if (shifts.isEmpty()) {
            return false;
        }
        for (Shift shift : shifts) {
            if (shift.getDate().equals(date))
                return true;
        }
        return false;
    }

    @Override
    public boolean isAppointmentAvailable(AppointmentDto appointmentDto) {
        Date appointmentDate = appointmentDto.getDate();
        DoctorDto doctorDto = appointmentDto.getDoctorDto();

        if (!isDoctorWorking(doctorDto, appointmentDate)) {
            return false; // Doctor is not working on that date
        }
        List<Shift> shifts = doctorDto.getShifts();
        for (Shift shift : shifts) {
            if (shift.getDate().equals(appointmentDate) && shift.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean confirmAppointment(AppointmentDto appointmentDto) {
        if (!isAppointmentAvailable(appointmentDto)) {
            return false;
        }
        Date appointmentDate = appointmentDto.getDate();
        DoctorDto doctorDto = appointmentDto.getDoctorDto();

        List<Shift> shifts = doctorDto.getShifts();
        for (Shift shift : shifts) {
            if (shift.getDate().equals(appointmentDate) && shift.isAvailable()) {
                // Update appointment status to confirmed
                Appointment appointment = AppointmentMapper.toEntity(appointmentDto);
                appointment.setStatus(true);
                // Decrement appointments count for the shift
                int appointmentsPerDay = shift.getAppointmentsPerDay();
                shift.setAppointmentsPerDay(appointmentsPerDay - 1);

                if (appointmentsPerDay == 0) {
                    shift.setAvailable(false);
                    appointment.setStatus(false);
                }

                return true; // Appointment confirmed successfully
            }
        }
        return false;
    }

    @Override
    public List<Appointment> findAllAppointment() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    @Override
    public Optional<Appointment> searchById(Long id) {
        return appointmentRepository.findById(id);
    }
}
