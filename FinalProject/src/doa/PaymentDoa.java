package doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pojo.Payment;

public class PaymentDoa {

	public List<Payment> getAllPayment(final Connection conn) {
		final List<Payment> payments = new ArrayList<>();
		try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM payment")) {
			final ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				final Payment payment = new Payment();
				payment.setPayId(resultSet.getInt("payId"));
				payment.setOrderId(resultSet.getInt("orderid"));
				payment.setAmount(resultSet.getFloat("amount"));
				payments.add(payment);
			}
		} catch (final Exception e) {

		}
		return payments;
	}

	public Payment deletePayment(final Connection conn, final Payment payment) {
		try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM payment WHERE payId = ?")) {
			preparedStatement.setInt(1, payment.getOrderId());

			final int count = preparedStatement.executeUpdate();
			if (count == 1) {
				payment.setMessage("Delet Successfully");
			} else {
				payment.setMessage("Unable to delete.");
				payment.setOptType(-4);
			}

		} catch (final Exception exception) {
			payment.setMessage(exception.getMessage());
			payment.setOptType(-4);
		}
		return payment;
	}

	public Payment updatePayment(final Connection conn, final Payment payment, final float amount) {
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("UPDATE payment SET amount = ? WHERE payId = ?")) {
			preparedStatement.setFloat(1, amount);
			preparedStatement.setInt(2, payment.getPayId());
			final int count = preparedStatement.executeUpdate();
			if (count == 1) {
				payment.setMessage("Update Successfully");
			} else {
				payment.setMessage("Unable to update.");
				payment.setOptType(-3);
			}
		} catch (final Exception e) {
			payment.setMessage(e.getMessage());
			payment.setOptType(-3);
		}
		return payment;
	}

	public Payment insertPayment(final Connection conn, final Payment payment) {
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("INSERT INTO payment(orderid, amount) VALUES(?,?)")) {
			preparedStatement.setInt(1, payment.getOrderId());
			preparedStatement.setFloat(2, payment.getAmount());
			final int count = preparedStatement.executeUpdate();
			if (count == 1) {
				payment.setMessage("Insert Successfully");
			} else {
				payment.setMessage("Unable to insert.");
				payment.setOptType(-2);
			}
		} catch (final Exception e) {
			payment.setMessage(e.getMessage());
			payment.setOptType(-2);
		}
		return payment;
	}
}
