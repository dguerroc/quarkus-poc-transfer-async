package com.edw.repository;

import com.edw.dto.TransferDto;
import com.edw.model.Transfer;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.PropertyKind;
import io.vertx.mutiny.sqlclient.SqlClient;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TransferRepository {

    @Inject
    MySQLPool client;

    @Transactional
    // Method to fetch a list of all transfers
    public Uni<List<Transfer>> find_All() {
        return client.query("SELECT * FROM T_TRANSFER")
                .execute()
                .onItem().transformToMulti(rowSet -> Multi.createFrom().iterable(rowSet))
                .onItem().transform(row -> {
                    Transfer transfer = new Transfer();
                    transfer.setAmount(row.getBigDecimal("amount"));
                    transfer.setAccountFrom(row.getString("accountFrom"));
                    transfer.setAccountTo(row.getString("accountTo"));
                    transfer.setTransferDate(Date.from(row.getLocalDate("transferDate")
                            .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    return transfer;
                })
                .collect().asList();
    }

    @Transactional
    public Uni<Void> save(TransferDto transferDto) {
        String query = "INSERT INTO T_TRANSFER (accountFrom,accountTo,amount,transferDate) VALUES (?, ?, ?, ?)";
          return client.preparedQuery(query).execute(Tuple.of(transferDto.getAccountFrom(),
                        transferDto.getAccountTo(),
                        transferDto.getAmount(),
                        LocalDateTime.now())).replaceWithVoid();
         //return client.preparedQuery("select MAX(id) as id  FROM db_test.T_TRANSFER").execute().onItem().transform(rowSet -> rowSet.iterator().next().getLong("id"));
    }
}
